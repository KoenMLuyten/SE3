package Domain.Core.ServiceHandlers;

import Domain.Entitities.Exceptions.ServiceException;
import Domain.Core.ServiceCaches.InfraCache;
import Domain.Entitities.Infrastructure.Section;
import Services.Interfaces.IInfrastructureService;


/*
* This class handles InfrastructureService, it checks whether it has the requested Section cached and otherwise makes a call to InfrastructureService to get te requested section, caching it in the process
* */
public class InfrastructureHandler implements IInfrastructureService{
    private InfraCache cache;
    private IInfrastructureService infraService;

    public InfrastructureHandler(IInfrastructureService infraService){
        this.infraService = infraService;
        this.cache = InfraCache.getCache();
    }

    public Section getSection(int sectionId){
        Section cachedSection = cache.get(sectionId);
        if (cachedSection != null){
            return cachedSection;
        }
        else {
            try {
                cachedSection = infraService.getSection(sectionId);
            }
            catch (ServiceException e){
            }
            cache.put(cachedSection.getSectionID(), cachedSection);
            return cachedSection;
        }

    }

}
