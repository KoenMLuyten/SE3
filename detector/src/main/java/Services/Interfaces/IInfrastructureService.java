package Services.Interfaces;

import Domain.Infrastructure.Section;
import Domain.ServiceException;

public interface IInfrastructureService {
    Section getSection(int sectionId) throws ServiceException;
}
