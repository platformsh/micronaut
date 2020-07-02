package sh.platform.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import java.util.List;

@ExecuteOn(TaskExecutors.IO)
@Controller("heroes")
public class HeroController {

    private final HeroService service;

    public HeroController(HeroService service) {
        this.service = service;
    }

    @Get("/{id}")
    public Hero show(Long id) {
        return service.findById(id).orElse(null);
    }

    @Get
    public List<Hero> findAll() {
        return service.findAll();
    }

    @Post
    public HttpResponse<Hero> save(@Body @Valid Hero hero) {
        return HttpResponse.created(service.save(hero));
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        service.deleteById(id);
        return HttpResponse.noContent();
    }
}
