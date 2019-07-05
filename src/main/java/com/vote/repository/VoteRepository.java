package com.vote.repository;

import com.vote.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    List<Vote> findAllByVoterAndIsDeleted(String voter,Boolean isDeleted);

    void deleteAllByVoter(String ip);

    @Modifying
    @Query("update Vote v set v.isDeleted = true where ip=?1")
    void updateIsDeleted(String ip);

    @Query(value = "select count(v) as voteCount, v.selection from Vote v where v.isDeleted<>1 group by v.selection")
    List<?> getVotesCountGroupBySelection();

}
