package com.catch42.Markov_Chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.catch42.Markov_Chatbot.model.MarkovChain;

public interface MarkovChainRepository extends CrudRepository<MarkovChain, Long> {

    /**
     * Get all entries in the repo
     * 
     * @return
     */
    @Query(" SELECT id, key, nextKey" + " FROM MarkovChain")
    public List<Object[]> getAllText();

    /**
     * Get all entries that match a given key
     * 
     * @param key
     * @return
     */
    @Query(" SELECt id, key, nextKey" + " FROM MarkovChain " + " WHERE key= :key")
    public List<MarkovChain> getTextByChannelAndKey(@Param("key") String key);

    /**
     * Pull the next possible chains given a key, and a list of previous chains that we don't want to replicate.
     * 
     * @param nextKeyValue
     * @param chains
     * @return
     */
    @Query("SELECT id, key, nextKey" + " FROM MarkovChain entry "
            + " WHERE entry.key = :nextKeyValue AND entry.key NOT IN (:chains)")
    public List<Object[]> getNextMarkovchain(@Param("nextKeyValue") String nextKeyValue,
            @Param("chains") List<String> chains);

    /**
     * This query exists, because postgres does not like in statements on null
     * 
     * @param nextKeyValue
     * @return
     */
    @Query("SELECT id, key, nextKey" + " FROM MarkovChain entry " + " WHERE entry.key = :nextKeyValue ")
    public List<Object[]> getNextMarkovchain(@Param("nextKeyValue") String nextKeyValue);

    /**
     * Get a particular entry by key and nextKey.
     * 
     * @param key
     * @param nextKey
     * @return
     */
    @Query(" SELECT id" + " FROM MarkovChain " + " WHERE key = :key AND nextKey = :nextKey ")
    public List<Object[]> getIdByKeyAndNextKey(@Param("key") String key, @Param("nextKey") String nextKey);

    /**
     * Get the current count of chains
     * 
     * @return
     */
    @Query(" SELECT COUNT(e)" + " FROM MarkovChain e")
    public List<Object> getMarkovChainCount();

    /**
     * Get a particular markov chain by id
     * 
     * @param id
     * @return
     */
    @Query(" SELECT id, key, nextKey " + " FROM MarkovChain e " + " WHERE id = :id ")
    public List<Object[]> getMarkovChainById(@Param("id") Long id);

}
