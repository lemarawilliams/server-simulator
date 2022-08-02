import java.util.*;

public class Main {

    public static class Server {
        LinkedList<Job> Q;
        Job jobInService;
        int nxtDepTime;

        public Server(LinkedList<Job> Q, Job jobInService, int nxtDepTime) {
            this.Q = Q;
            this.jobInService = jobInService;
            this.nxtDepTime = nxtDepTime;
        }
    }

    public static class Job {
        int arrTime;
        int size;
        int depTime;

        public Job(int p, int q, int r) {
            arrTime = p;
            size = q;
            depTime = r;
        }
    }

    public static void main(String[] args) {
        int currTime = 0;  //current time in simulation
        int numOfJobs = 0; // number of jobs in queue
        int jobsArrived = 0; //total # of jobs that entered queue
        int jobsDone = 0; //total # jobs that left server
        LinkedList<Job> Q = new LinkedList<Job>();
        Server server = new Server(Q, null, 0);

        int jobsInService = 0; // total # of jobs that got put in server
        int totalRespTime = 0;
        currTime = 0;

        server.nxtDepTime = 0;
        int nxtArrTime = 0;

        while (jobsArrived != 100000) {

            if (nxtArrTime < server.nxtDepTime) { //next job is arrival
                currTime = nxtArrTime;
                numOfJobs++;
                Q.add(new Job(RV_gen(0.45), RV_gen(0.5), 0));
                Q.get(numOfJobs - 1).arrTime = nxtArrTime;
                Q.get(numOfJobs - 1).depTime = Q.get(numOfJobs - 1).arrTime + Q.get(numOfJobs - 1).size;
                if (jobsArrived > 2000) {
                    if (server.jobInService != null) {
                        jobsInService += Q.size(); // includes job in service, doesn't include new job
                    }
                    else{
                        jobsInService += Q.size() - 1; // size of queue, not including new job
                    }
                }
                jobsArrived++;
                if (server.jobInService == null) {
                    server.jobInService = Q.element();
                    Q.remove();
                    numOfJobs--;
                    server.nxtDepTime = currTime + server.jobInService.size;
                }

                nxtArrTime = currTime + RV_gen(0.45);
            }

            else if (server.nxtDepTime < nxtArrTime) { //next job is departure
                currTime = server.nxtDepTime;
                if (server.jobInService != null && jobsArrived > 2000) {
                    totalRespTime += currTime - server.jobInService.arrTime;
                }
                if (server.jobInService != null && jobsArrived > 2000) {
                    jobsDone++;
                }
                server.jobInService = null; //removing job

                if (!Q.isEmpty()) {
                    server.jobInService = Q.element();
                    Q.remove();
                    numOfJobs--;
                    server.nxtDepTime = currTime + server.jobInService.size;
                } else {
                    server.nxtDepTime = currTime + RV_gen(0.5);
                }

            }
            else { //when nxtArrTime == server.nxtDepTime

                //departure
                currTime = server.nxtDepTime;
                if (server.jobInService != null && jobsArrived > 2000) {
                    totalRespTime += currTime - server.jobInService.arrTime;
                }
                if (server.jobInService != null && jobsArrived > 2000) {
                    jobsDone++;
                }
                server.jobInService = null; // remove job
                if (!Q.isEmpty()) {
                    server.jobInService = Q.element();
                    Q.remove();
                    numOfJobs--;
                    server.nxtDepTime = currTime + server.jobInService.size;
                }

                //arrival
                currTime = nxtArrTime;
                numOfJobs++;
                Q.add(new Job(RV_gen(0.45), RV_gen(0.5), 0));
                Q.get(numOfJobs - 1).arrTime = nxtArrTime;
                Q.get(numOfJobs - 1).depTime = Q.get(numOfJobs - 1).arrTime + Q.get(numOfJobs - 1).size;
                if (jobsArrived > 2000) {
                    if (server.jobInService != null) {
                        jobsInService += Q.size(); // includes job in service, doesn't include new job
                    }
                    else{
                        jobsInService += Q.size() - 1; // size of queue, not including new job
                    }
                }
                jobsArrived++;
                if (server.jobInService == null) {
                    server.jobInService = Q.element();
                    Q.remove();
                    numOfJobs--;
                    server.nxtDepTime = currTime + server.jobInService.size;
                }

                nxtArrTime = currTime + RV_gen(0.45);
            }
        }

        System.out.println("Current time is: " + currTime);
        System.out.println("Size of queue is: " + Q.size());
        System.out.println("Number of jobs are: " + numOfJobs);
        System.out.println("Total jobs ran are: " + jobsArrived);
        System.out.println("Total jobs in service are: " +jobsInService);
        System.out.println("Total jobs completed are: " +jobsDone);

        System.out.println("Total Response Time is: " + (double) totalRespTime);
        System.out.println("E[N] is: " + (double) jobsInService / (jobsArrived - 2000));
        System.out.println("E[T] is: " + (double) totalRespTime / jobsDone);

    }

    public static int RV_gen(double p) {
        int geoResult = 0;
        int i = 0;
        // generate random number from 0 to 1
        double int_random = Math.random();

        while (geoResult == 0) {
            double val_1 = 1 - (Math.pow((1 - p), (i - 1)));
            double val_2 = 1 - (Math.pow((1 - p), i));
            if (val_1 < int_random && int_random < val_2) {
                geoResult = i;
            }
            i++;
        }
        return geoResult;
    }

}
