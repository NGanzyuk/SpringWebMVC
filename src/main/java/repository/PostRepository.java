package repository;

import model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository implements PostRepositoryInterface {
    private final ConcurrentHashMap<Long, Post> rep = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Post> all() {
        return new ArrayList<>(rep.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(rep.get(id));
    }

    public Post save(Post post) {
        long postId = post.getId();
        if (postId == 0) {
            long newPostId = idCounter.getAndIncrement();
            post.setId(newPostId);
            rep.put(newPostId, post);
        } else {
            rep.put(postId, post);
        }
        return post;
    }

    public void removeById(long id) {
        rep.remove(id);
    }
}
