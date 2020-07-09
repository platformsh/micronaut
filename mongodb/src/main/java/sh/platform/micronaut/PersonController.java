package sh.platform.micronaut;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.bson.conversions.Bson;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller("/people")
public class PersonController {

    private final MongoClient mongoClient;

    public PersonController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Get("/{name}")
    public Person show(String name) {
        Bson filter = Filters.eq("name", name);
        return getCollection().find(filter).first();
    }

    @Get
    public Iterable<Person> findAll() {
        final FindIterable<Person> iterable = getCollection().find();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Post
    public HttpResponse<Person> save(@Body @Valid Person person) {
        getCollection().insertOne(person);
        return HttpResponse.created(person);
    }

    @Delete("/{name}")
    public HttpResponse delete(String name) {
        Bson filter = Filters.eq("name", name);
        getCollection().deleteOne(filter);
        return HttpResponse.noContent();
    }

    private MongoCollection<Person> getCollection() {
        return mongoClient
                .getDatabase("main")
                .getCollection("people", Person.class);
    }
}
