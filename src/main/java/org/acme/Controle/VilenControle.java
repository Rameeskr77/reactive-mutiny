package org.acme.Controle;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.Reposetory.VilenRepo;
import org.acme.Service.villenService;
import org.acme.model.Vilen;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/villen")
public class VilenControle {
    @Inject
    villenService vilens;
    @Inject
    VilenRepo vrepo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Vilen>  getVilen(){
        return vilens.get().onItem()
                .transformToMulti( list -> Multi.createFrom().iterable(list));
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<Vilen> addVillen(Vilen vilen){
     return   Uni.createFrom().item(vilen)
               .onItem().transformToUni(item->vilens.add(item))
               .onItem().transformToUni(item-> Uni.createFrom().item(vilen));
    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public  Uni<Vilen> getOne(@PathParam("id") long id){
    return vilens.findone(id);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("{id}")
    public  Uni<Vilen> deletevilen(@PathParam("id") long id){
        Uni.createFrom().item(vrepo.findById(id))
                .onItem().transformToMulti(item ->Multi.createFrom().items(item))
                .select().when(item ->Uni.createFrom().item(item.getId()==id))
                .subscribe().with(v -> vilens.deleteV(v.getId()),filur -> System.out.println("if no item"));
                return Uni.createFrom().nullItem();
    }
    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public  Uni<Vilen> update(Vilen vilen){
     return Uni.createFrom().item(vilen)
            .onItem().call(()->vilens.update(vilen));
     }

}
