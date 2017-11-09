package Services.Interfaces;

import Domain.Entitities.Infrastructure.Section;
import Domain.Entitities.Exceptions.ServiceException;

/*
* interface representing an adaptation of the provide InfrastructureService
* */
public interface IInfrastructureService {
    Section getSection(int sectionId) throws ServiceException;
}
