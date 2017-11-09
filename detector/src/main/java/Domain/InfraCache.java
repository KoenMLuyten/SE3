package Domain;

import Domain.Infrastructure.Section;

import java.util.concurrent.ConcurrentHashMap;


/*
* This class represents a Cache for the Infrastructure Handler to cache all the Sections he has already retrieved from InfrastructureService
* */
public class InfraCache extends ConcurrentHashMap<Integer, Section>{
    private static final InfraCache CACHE = new InfraCache();

    private InfraCache(){}

    public static InfraCache getCache(){return CACHE;}
}
