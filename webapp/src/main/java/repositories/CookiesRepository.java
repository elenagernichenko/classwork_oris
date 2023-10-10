package repositories;
import java.util.UUID;

public interface CookiesRepository {
    void saveSession(UUID uuid, long userId);
    boolean findSession(UUID uuid);
}
