package com.vote.repository;

import com.vote.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    List<Vote> findAllByIpAndIsDeleted(String ip,Boolean isDeleted);

    void deleteAllByIp(String ip);

    @Modifying
    @Query("update Vote v set v.isDeleted = true where ip=?1")
    void updateIsDeleted(String ip);

    @Query(value = "select count(v) as cnt, v.name from Vote v where v.isDeleted<>1 group by v.name")
    List<?> getNamesGroupByName();

}
