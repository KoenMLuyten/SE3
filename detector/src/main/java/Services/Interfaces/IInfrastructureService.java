package Services.Interfaces;

import Domain.Infrastructure.Section;
import Domain.ServiceException;

/*
* interface representing an adaptation of the provide InfrastructureService
* */
public interface IInfrastructureService {
    Section getSection(int sectionId) throws ServiceException;
}
