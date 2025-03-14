package website.ylab.financetracker.adapter.out.persistence.target;

public interface TargetRepository {
    TargetEntity create (TargetEntity target);
    TargetEntity getById(long id);
    TargetEntity delete(long id);
}