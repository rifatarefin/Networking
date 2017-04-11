#include<bits/stdc++.h>
#include<cstdlib>
#include<string>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
using namespace std;
#define NULL_VALUE 9999999
#define NO_ROUTER "---.---.--.-"

void show(string myip);
void init(string myip);
void recvTable(string s,string myip);

map<string,int> nbor_edge_cost;
map<string,int> nbor_edge_sentcount;

struct node
{
    string dest;
    string next_hop;
    int cost;
};

vector< struct node >RoutingTable;
set<string>AllRouter,neighbor;

int main(int argc, char* argv[])
{
    if(argc != 2)
    {
        printf("%s <ip address>\n", argv[0]);
        exit(1);
    }


    string myip=argv[1];
    init(myip);
    int myNeighbor=neighbor.size();
    int sockfd;
    int bind_flag;
    int bytes_received;
    int sentbytes;

    socklen_t addrlen;
    char buffer[1024];
    struct sockaddr_in others_address[myNeighbor];
    struct sockaddr_in my_address;

    my_address.sin_family = AF_INET;
    my_address.sin_port = htons(4747);

    inet_pton(AF_INET,argv[1],&my_address.sin_addr);



    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    bind_flag = bind(sockfd, (struct sockaddr*) &my_address, sizeof(sockaddr_in));
    if(bind_flag==0)printf("successful bind\n");


    while(true){


        bytes_received = recvfrom(sockfd, buffer, 1024, 0, (struct sockaddr*) &others_address, &addrlen);

        string s=string(buffer);
        string first=s.substr(0,4);
        //cout<<"\n"<<first<<" received"<<"\n";



        if(first=="show")show(myip);
        else if(first=="clk ")
        {

            set<string>::iterator st;
            int i;
            vector<struct node>::iterator v;
            for(st=neighbor.begin(),i=0;st!=neighbor.end();i++,st++)
            {

                others_address[i].sin_family = AF_INET;
                others_address[i].sin_port = htons(4747);
                string p=*st;
                if(nbor_edge_sentcount[p]==3)
                {
                    for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
                    {
                        if((*v).next_hop==p)
                        {
                            (*v).next_hop=NO_ROUTER;
                            (*v).cost=NULL_VALUE;
                        }
                    }
                }
                nbor_edge_sentcount[p]++;

                inet_pton(AF_INET,p.c_str(), &others_address[i].sin_addr);
                char ac[1024];
                string sq="";
                for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
                {

                    int a=(*v).cost;
                    stringstream ss;
                    ss << a;
                    string str = ss.str();
                    sq+="exch "+myip+" "+(*v).dest+" "+(*v).next_hop+" "+str+" ";



                }
                strcpy(ac,sq.c_str());
                sentbytes=sendto(sockfd,ac,1024,0,(struct sockaddr*) &others_address[i],sizeof(sockaddr_in));



            }


        }

        else if (first=="exch")
        {

            recvTable(s,myip);


        }
        else if(first=="cost")
        {


            char msg[30],msg2[30];
            inet_ntop(AF_INET, buffer + 4, msg, sizeof(msg));
            inet_ntop(AF_INET, buffer + 8, msg2, sizeof(msg2));
            string ip1=string(msg);
            string ip2=string(msg2);

            int c=buffer[12];
            int d=buffer[13];
            c+=(d<<8);

            string my_nbor;
            if(ip1==myip)
            {
                my_nbor=ip2;
            }
            else
            {
                my_nbor=ip1;
            }

            vector<struct node>::iterator v;
            for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
            {
                if((*v).next_hop==my_nbor)
                {
                    (*v).cost=(*v).cost-nbor_edge_cost[my_nbor]+c;

                }

            }
            nbor_edge_cost[my_nbor]=c;



        }

        else if(first=="send")
        {

            char msg[30],msg2[30];
            inet_ntop(AF_INET, buffer + 4, msg, sizeof(msg));
            inet_ntop(AF_INET, buffer + 8, msg2, sizeof(msg2));
            string ip1=string(msg);
            string ip2=string(msg2);

            int c=buffer[12];
            int d=buffer[13];
            c+=(d<<8);
            cout<<c<<"\n";
            char cd[c];
            int i;
            for(i=0;i<c;i++)
            {
                int e=buffer[14+i];
                cd[i]=e;
            }
            cd[i]='\0';
            string pass=string(cd);

            vector<struct node>::iterator v;
            for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
            {
                if((*v).dest==ip2)
                {
                    others_address[0].sin_family = AF_INET;
                    others_address[0].sin_port = htons(4747);
                    string p=(*v).next_hop;
                    inet_pton(AF_INET,p.c_str(), &others_address[0].sin_addr);

                    char ac[1024];
                    stringstream ss;
                    ss << c;
                    string cstr = ss.str();

                    string sq="frwd "+ip2+" "+cstr+" "+pass+"\n";
                    strcpy(ac,sq.c_str());
                    cout<<pass<<" packet forwarded to "<<p<<"\n";

                    sentbytes=sendto(sockfd,ac,1024,0,(struct sockaddr*) &others_address[0],sizeof(sockaddr_in));
                    break;

                }
            }

        }

        else if(first=="frwd")
        {
           
            string buf; // Have a buffer string
            stringstream ss(s); // Insert the string into a stream

            vector<string> tokens; // Create vector to hold our words

            while (ss >> buf)
                tokens.push_back(buf);

            string ip2=tokens[1];
            string len=tokens[2];
            string msg=tokens[3];
            if(ip2==myip)
            {
                cout<<msg<<" packet reached destination\n";
            }
            else
            {
                vector<struct node>::iterator v;
                for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
                {
                    if((*v).dest==ip2)
                    {
                        others_address[0].sin_family = AF_INET;
                        others_address[0].sin_port = htons(4747);
                        string p=(*v).next_hop;
                        inet_pton(AF_INET,p.c_str(), &others_address[0].sin_addr);

                        char ac[1024];
                        

                        string sq="frwd "+ip2+" "+len+" "+msg+"\n";
                        strcpy(ac,sq.c_str());
                        cout<<msg<<" packet forwarded to "<<p<<"\n";

                        int sentbytes=sendto(sockfd,ac,1024,0,(struct sockaddr*) &others_address[0],sizeof(sockaddr_in));
                        break;

                    }
                }
            }


        }



        if(!strcmp(buffer, "shutdown")) break;
    }

    close(sockfd);




}

void init(string myip)
{
    RoutingTable.clear();
    ifstream fin;
    fin.open("topo.txt");
    struct node temp;


    while (!fin.eof())
    {
        string ip1,ip2;int cost;

        fin>>ip1>>ip2>>cost;

        if(ip1==myip)
        {
            temp.dest=ip2;
            temp.next_hop=ip2;
            temp.cost=cost;
            neighbor.insert(ip2);
            nbor_edge_cost[ip2]=cost;
            nbor_edge_sentcount[ip2]=0;
            AllRouter.insert(ip2);
            RoutingTable.push_back(temp);
        }

        else if(ip2==myip)
        {
            temp.dest=ip1;
            temp.next_hop=ip1;
            temp.cost=cost;
            neighbor.insert(ip1);
            nbor_edge_sentcount[ip1]=0;
            nbor_edge_cost[ip1]=cost;
            AllRouter.insert(ip1);
            RoutingTable.push_back(temp);
        }

        else
        {
            AllRouter.insert(ip1);
            AllRouter.insert(ip2);
        }


    }

    struct node tem;
    tem.dest=myip;
    tem.next_hop=myip;
    tem.cost=0;
    RoutingTable.push_back(tem);


    set<string>::iterator s;
    for(s=AllRouter.begin();s!=AllRouter.end();s++)
    {
        if(neighbor.find(*s)==neighbor.end())
        {
            temp.dest=*s;
            temp.next_hop=NO_ROUTER;
            temp.cost=NULL_VALUE;
            RoutingTable.push_back(temp);
        }

    }
    show(myip);
    fin.close();


}
void show(string myip)
{
    cout<<"\nRouting Table for "<<myip<<"\n";
    cout<<"Destination\tNext Hop\tCost\n\n";
    vector<struct node>::iterator v;
    for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
    {
        cout<<(*v).dest<<"\t"<<(*v).next_hop<<"\t"<<(*v).cost<<"\n";
    }
}
void recvTable(string s,string myip)

{
    string buf; // Have a buffer string
    stringstream ss(s); // Insert the string into a stream

    vector<string> tokens; // Create vector to hold our words

    while (ss >> buf)
        tokens.push_back(buf);
    int table_len=RoutingTable.size();

    for(int i=0;i<table_len;i++)
    {
        string nbor=tokens[1+5*i];
        string dest=tokens[2+5*i];
        string nhop=tokens[3+5*i];
        string cost=tokens[4+5*i];
        int cost2=stoi(cost);

        int cost1 = nbor_edge_cost[nbor];
        nbor_edge_sentcount[nbor]=0;
        vector<struct node>::iterator v;

        //cout<<"nbor: "<<nbor<<"\ndest: "<<dest<<"\nnhop: "<<nhop<<"\ncost "<<scost<<"\n";


        for(v=RoutingTable.begin();v!=RoutingTable.end();v++)
        {
            if((*v).dest==dest)
            {
                if(nbor==(*v).next_hop||(cost1+cost2<(*v).cost && myip!=nhop))
                {
                    (*v).cost=cost1+cost2;
                    (*v).next_hop=nbor;
                    //cout<<"updating: "<<(*v).cost<<" "<<(*v).next_hop<<"\n";
                }
            }
        }

    }
    

}

