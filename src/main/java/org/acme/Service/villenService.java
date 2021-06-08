package org.acme.Service;

import io.smallrye.mutiny.Uni;
import org.acme.Reposetory.VilenRepo;
import org.acme.model.Vilen;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class villenService {
    @Inject
    VilenRepo vrepo;

    public Uni<List<Vilen>> get(){
    return   Uni.createFrom().item(vrepo.listAll());
    }

    public Uni<Void> add(Vilen vilen){
        Uni.createFrom().item(vilen)
                .onItem().invoke(item ->vrepo.persist(item))
                .subscribe().with(vilen1 -> System.out.println("sucssesfully added"), filer-> System.out.println("boom"+filer));
        return Uni.createFrom().nullItem();
    }
    public Uni<Vilen> findone(long id){
     return
             Uni.createFrom().item(vrepo.findById(id));
    }

    public Uni<Void> deleteV(long id){
        System.out.println(id);
        Uni.createFrom().item(vrepo.deleteById(id));
        return Uni.createFrom().nullItem();
    }

    public Uni<Void> update(Vilen v){
      Uni.createFrom().item(v)
              .subscribe().with(vilen -> {
                 int value= vrepo.update("name=?1 where id=?2",vilen.getName(),vilen.getId());
                 if (value==0){
                     Vilen v2=new Vilen();
                     v2.setName(v.getName());
                     vrepo.persist(v2);
                 }
                  }
              , System.out::println);
     return Uni.createFrom().nullItem();
    }

}
