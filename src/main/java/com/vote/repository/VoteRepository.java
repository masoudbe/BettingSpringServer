package com.vote.repository;

import com.vote.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    List<Vote> findAllByVoterAndIsDeleted(String voter,Boolean isDeleted);

    void deleteAllByVoter(String voter);

    @Modifying
    @Query("update Vote v set v.isDeleted = true where voter=?1 and length(v.selection) = ?2 ")
    void updateIsDeleted(String voter, Integer length);

    @Query(value = "select count(v) as voteCount, v.selection from Vote v where v.isDeleted<>1 group by v.selection")
    List<?> getVotesCountGroupBySelection();

}
