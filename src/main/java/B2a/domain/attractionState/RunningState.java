package B2a.domain.attractionState;

import B2a.domain.attraction.Attraction;

public class RunningState extends State {
    Attraction attraction;


    public RunningState(Attraction attraction){
        this.attraction = attraction;

    }

    @Override
    public void stop() {
        System.out.println("Stopping the attraction");
        attraction.setState(new WaitingState(attraction));

    }


    @Override
    public void damaged(){
        System.out.println("attraction was damaged");
        attraction.setState(new DefectState(attraction));
    }

}