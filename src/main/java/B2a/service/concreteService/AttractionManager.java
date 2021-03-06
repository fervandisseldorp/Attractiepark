package B2a.service.concreteService;

import B2a.domain.attraction.*;
import B2a.domain.attractionState.State;
import B2a.repository.AttractionRepository;
import B2a.repository.StateRepository;
import B2a.service.abstractService.AttractionManagerIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class AttractionManager implements AttractionManagerIF{
    private AttractionBuilder builder;

    @Autowired
    private final AttractionRepository attractionRepository;
    private final StateRepository stateRepository;

    public AttractionManager(AttractionRepository attractionRepository, StateRepository stateRepository){
        this.attractionRepository = attractionRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public Attraction createNewAttraction(String type) {
        switch(type){
            case "rollercoaster":
                System.out.println("rollercoaster builder set");
                builder = new RollercoasterBuilder();
                break;
            case "pendulum":
                System.out.println("pendulum builder set");
                builder = new PendulumBuilder();
                break;
            case "waterattraction":
                System.out.println("water builder set");
                builder = new WaterBuilder();
                break;
            default:
                System.out.println("Default reached");
        }
        Attraction attraction = builder.createNewAttraction(); // print constructor
        System.out.println("Building a new attraction and saving it");
        attractionRepository.save(attraction);
        return attraction;
    }

    @Override
    public void saveAttraction(Attraction attraction) {
        attractionRepository.save(attraction);
    }

    @Override
    public Iterable<Attraction> findAllAttractions() {
        return attractionRepository.findAll();
    }


    // FIND ONE ATTRACTION BY IT'S ID
    @Override
    public Attraction findAttraction(Long id){
        return attractionRepository.findOne(id);
    }

    //GETS ALL ATTRACTIONTYPES SO BUTTONS IN THE CHOOSER CAN BE CREATED INSIDE A LOOP
    public Iterable<String> findAllAttractionTypes(){
        Iterable<Attraction> attractions = attractionRepository.findAll();
        ArrayList<String> returnIter = new ArrayList();
        for(Attraction a : attractions){
            boolean inList = false;

            if(inList == false){
                returnIter.add(a.getTransportType() );
            }
        }

        return null;
    }


    @Transactional
    public void changeState(Attraction attraction, String action){
        State oldState = stateRepository.save( attraction.getCurrentState() );
        long oldId = oldState.getId();

        switch(action){
            case "open":
                attraction.open();
                break;
            case "start":
                attraction.start();
                break;
            case "stop":
                attraction.stop();
                break;
            case "close":
                attraction.close();
                break;
            case "damage":
                attraction.damaged();
                break;
            case "repair":
                attraction.repair();
                break;
        }

        State newState = attraction.getCurrentState();
        newState.setAttraction( attraction );
        stateRepository.save( newState  ); // Give newstate an id.
        stateRepository.delete( oldId );
        //stateRepository.delete( oldState.getId() );
    }

    @Override
    public void deleteState(Long id) {
        stateRepository.delete(id);
    }
}
