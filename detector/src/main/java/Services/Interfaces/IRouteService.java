package Services.Interfaces;

import Domain.Infrastructure.Route;
import Domain.ServiceException;

public interface IRouteService {
    public Route getRoute(int rideId) throws ServiceException;
}
