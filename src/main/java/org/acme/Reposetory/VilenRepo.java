package org.acme.Reposetory;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.model.Vilen;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VilenRepo implements PanacheRepository<Vilen> {
}
