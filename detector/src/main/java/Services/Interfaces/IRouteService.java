package Services.Interfaces;

import Domain.Entitities.Infrastructure.Route;
import Domain.Entitities.Exceptions.ServiceException;

public interface IRouteService {
    public Route getRoute(int rideId) throws ServiceException;
}
