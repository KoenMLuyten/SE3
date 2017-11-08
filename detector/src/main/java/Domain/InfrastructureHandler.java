package Domain;

import Domain.Infrastructure.Section;
import Services.Implementations.InfraService;
import Services.Interfaces.IInfrastructureService;
import Services.Interfaces.IRouteService;

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
