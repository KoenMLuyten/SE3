package Domain;

import Domain.Infrastructure.Section;

import java.util.concurrent.ConcurrentHashMap;

public class InfraCache extends ConcurrentHashMap<Integer, Section>{
    private static final InfraCache CACHE = new InfraCache();

    private InfraCache(){}

    public static InfraCache getCache(){return CACHE;}
}
