package resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import filters.CORSResponseFilter;

/**
 * 
 * @author Victor Silva
 * @since 25-03-2018
 */

@ApplicationPath("/api")
public class RestApplication extends Application{
	
	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(CORSResponseFilter.class);
        resources.add(ImovelComercialResource.class);
        resources.add(UsuarioResource.class);
        resources.add(ImovelResidencialResource.class);
        resources.add(ProprietarioResource.class);
        return resources;
    }

}