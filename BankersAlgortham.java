import java.util.Scanner;

public class BankersAlgortham {
    int MAX = 20;
    int nop, nor, k, pnum;
    int al[][] = new int[MAX][MAX];
    int m[][] = new int[MAX][MAX];
    int n[][] = new int[MAX][MAX];
    int avail[] = new int[MAX];
    int instances[] = new int[MAX];
    int add[] = new int[MAX];
    int result[] = new int[MAX];
    int work[] = new int[MAX];
    int finish[] = new int[MAX];
    int request[] = new int[MAX];
    Scanner sc = new Scanner(System.in);


    public void initialize(){
        k = 0;
        for (int i=0; i<MAX;i++ ){
            for (int j=0; j<MAX; j++){
                al[i][j] =0;
                m[i][j] =0;
                n[i][j] =0;
            }
            avail[i] =0;
            add[i] =0;
            request[i]=0;
            result[i] =0;
            finish[i] =0;
        }
    }


    public void getInput(){
        int i,j;
        System.out.print("Enter the number of processes:");
        nop = sc.nextInt();
        System.out.print("Enter the number of resources:");
        nor=sc.nextInt();
        System.out.println("Enter the total available instsnces of each resources in the system: ");
        for(j=0;j<nor;j++) {
            System.out.print("Resource "+j+":");
            instances[j] = sc.nextInt();
            work[j]=-1;
        }

        System.out.println("Enter the allocated resources for each process: ");
        for(i=0;i<nop;i++){
            System.out.print("\nProcess "+i);
            for(j=0;j<nor;j++){
                System.out.print("\nResource "+j+":");
                al[i][j] = sc.nextInt();
                add[j]+=al[i][j];
            }
        }
        for(j=0;j<nor;j++){
            avail[j]=instances[j]-add[j];
        }

        System.out.println("Enter the maximum resources that are needed for each process: ");
        for(i=0;i<nop;i++){
            System.out.print("\nProcess "+i);
            for(j=0;j<nor;j++){
                System.out.print("\nResouce "+j+":");
                m[i][j] = sc.nextInt();
                n[i][j]=m[i][j]-al[i][j];
            }

        }


        for(i=0;i<nop;i++) {
            finish[i] = 0;
        }
    }


    public int search(int i){
        int j;
        for(j=0;j<nor;j++) {
            if (n[i][j] > avail[j]) {
                return -1;
            }
        }
        return j;

    }

    public void method(){
        int i=0,j,flag;
        while(true) {
            if(finish[i]==0) {
                pnum=search(i);
                if(pnum!=-1) {
                    result[k++]=i;
                    finish[i]=1;
                    for(j=0;j<nor;j++){
                        avail[j]=avail[j]+al[i][j];
                    }
                }
            }
            i++;
            if(i==nop) {
                flag = 0;
                for (j = 0; j < nor; j++) {
                    if (avail[j] != work[j]) {
                        flag = 1;
                    }
                }
                for(j=0;j<nor;j++) {
                    work[j] = avail[j];
                }
                if(flag==0) {
                    break;
                } else {
                    i = 0;
                }
            }
        }
    }

    public int display(){

        int i,j;
        System.out.print("\nTotal used resources are \n");
        for(j=0;j<nor;j++) {
            if(add[j]>instances[j])
                return 0;
            else
                System.out.print((add[j]+request[j])+"  ");
        }
        System.out.print("\nResources Available at Current Time are \n");
        for(j=0;j<nor;j++) {
            System.out.print((instances[j]-(add[j]+request[j]))+"  ");
            avail[j]=request[j];
        }

        System.out.print("\n\nOUTPUT:\n");

        System.out.print("\n========\n");

        System.out.print("\nPROCESS\t     ALLOTED\t     MAXIMUM\t     NEED");

        for(i=0;i<nop;i++) {
            System.out.print("\nP"+i+"\t     ");
            for(j=0;j<nor;j++) {
                System.out.print(al[i][j]+"  ");
            }
            System.out.print("\t     ");
            for (j=0;j<nor;j++) {
                System.out.print(m[i][j]+"  ");
            }
            System.out.print("\t     ");
            for(j=0;j<nor;j++ ) {
                System.out.print(n[i][j]+"  ");
            }
        }
        System.out.println();
        System.out.print("The sequence of the safe processes are: \n");
        int temp;
        for(i=0;i<k;i++) {
            temp = result[i];
            System.out.print("P"+temp+" ");
        }

        int flg=0;

        for (i=0;i<nop;i++) {
            if(finish[i]==0) {
                flg=1;
            }
            System.out.print("\nRESULT:");
            System.out.print("\n=======");

            if(flg==1) {
                System.out.print("\nThe system is not in safe state and deadlock may occur!!");
                return 1;
            } else {
                System.out.print("\nThe system is in safe state and deadlock will not occur!!");
                return 1;
            }
        }
        return 1;
    }

    public int requestR(){
        int process,j,i;
        k=0;
        for( i=0;i<MAX;i++) {
            result[i]=0;
        }
        System.out.print("\nEnter Process Number Which Request Resorces\n");
        process = sc.nextInt();
        System.out.print("Enter instances needed by process "+process+"\n");
        for(j=0;j<nor;j++) {
            System.out.print("\nResouce "+j+":");
            request[j] = sc.nextInt();
            work[j]=-1;
            // add[j]+=request[j];
        }
        for(j=0;j<nor;j++) {
            if(request[j]>n[process][j]) {
                return 0;
            }
            if(request[j]>instances[j]-add[j]) {
                return 0;
            } else {
                continue;
            }
        }

        for(j=0;j<nor;j++) {
            al[process][j]+=request[j];
            avail[j]=instances[j]-add[j];
        }


        for(i=0;i<nop;i++) {
            for(j=0;j<nor;j++) {
                n[i][j]=m[i][j]-al[i][j];
            }
        }

        for(i=0;i<nop;i++)
            finish[i]=0;
        return 1;
    }


    public static void main(String[]arg){
        System.out.println("DEADLOCK AVOIDENCE BANKER'S ALGORTHAM ");
        BankersAlgortham ba = new BankersAlgortham();
        ba.initialize();
        ba.getInput();
        ba.method();
        int i= ba.display();
        if(i == 0){
            System.out.println("Sorry Entered Resources are Greater than total resources");
        }
        int j = ba.requestR();
        if(j == 0){
            System.out.println("Sorry Entered Resources are Greater than Availble OR Needed resources");
        }
        ba.method();
        ba.display();
    }
}
