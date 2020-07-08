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

@ExecuteOn(TaskExecutors.IO)
@Controller("/books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @Get("/{id}")
    public Book show(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Get
    public Iterable<Book> findAll() {
        return repository.findAll();
    }

    @Post
    public HttpResponse<Book> save(@Body @Valid Book book) {
        return HttpResponse.created(repository.save(book));
    }

    @Delete("/{id}")
    public HttpResponse delete(Long id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }

}
