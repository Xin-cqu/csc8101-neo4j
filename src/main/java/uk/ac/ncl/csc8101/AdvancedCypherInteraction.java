package uk.ac.ncl.csc8101;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Class consisting of simple methods to set the complex Cypher
 * query strings to be executed.
 *
 * @author Hugo Firth <h.firth @ ncl.ac.uk>
 * @since 2014-01-24
 * @see CypherInteraction
 */
public class AdvancedCypherInteraction extends CypherInteraction {

    public AdvancedCypherInteraction(GraphDatabaseService db) {
        super(db);
    }

    /**
     * Description: Return the 10 users who follow the greatest number of the
     * same users as user @majorlyprofound, in descending order of the number of same users followed.
     * These users could be thought of as being 'similar' to @majorlyprofound.
     *
     * Hint: These 10 users do not necessarily have to follow or be followed by @marjorlyprofound.
     *
     * Student Rationale:
     * x represents users followed by @majorlyprofound and other users(a)
     * count(x) and return a so I get the result.
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction  similarUsersByFollows()
    {
        this.query = "MATCH (u:User{username:\"@majorlyprofound\"})-[:FOLLOWS]->(x:User)<-[:FOLLOWS]-(a:User) WITH a,count(x) as num ORDER BY num DESC LIMIT 10 return a ";
        return this;
    }

    /**
     * Description:
     *
     * Building upon what you learned in the previous query - return upto 10 users who are:
     *
     * - Followed by those 10 users who follow the largest number of the same other
     * users as @majorlyprofound (called similar users).
     *
     * - Whom @majorlyprofound does not follow yet.
     *
     * - Sorted in descending order of occurrences (the number of similar users who follow each).
     *
     * Hint: You can have multiple sets of MATCH WHERE WITH ORDER clauses in a query
     * Note: Make sure you do not recommend that @majorlyprofound follows himself!
     *
     * Student Rationale:
     *  Just as the former one, now I need add another MATCH which rules as following:
     *  users who are not followed by @majorlyprofound but followed by the similar users as
     *  @majorlyprofound, then kick out @majorlyprofound himself at "where".
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction  potentialFollowsBySimilarUsers()
    {
        this.query = "MATCH (u:User{username:\"@majorlyprofound\"})-[:FOLLOWS]->(x:User)<-[:FOLLOWS]-(a:User) WITH a,count(x) as num ORDER BY num DESC LIMIT 10 MATCH(u:User{username:\"@majorlyprofound\"}), a-[:FOLLOWS]->(b:User) where NOT u-[:FOLLOWS]->b and (NOT b.username=\"@majorlyprofound\")   WITH b, count(b) as pos ORDER BY pos DESC LIMIT 10 return b";
        return this;
    }

    /**
     * Description: Building upon what you learned in the previous queries - return upto 10 users who are:
     *
     * - Followed by the largest number of those 10 users who follow the largest number of the same other
     * users as @majorlyprofound (called similar users).
     *
     * - Whom @majorlyprofound does not follow yet.
     *
     * - Who tweet with hashtags that @marjorlyprofound also tweets with.
     *
     * - Sorted in descending order of the number of times @majorlyprofound has used a hashtag, and
     * the number of times a user has used that same hashtag.
     *
     * Hint: You can order by multiple variables (e.g ORDER BY thisN, thenThisN ...)
     * Note: Make sure you do not recommend that @majorlyprofound follows himself!
     *
     * Student Rationale:
     *  Now I need another more MATCH which handle the tweets by @majorlyprofound  also the users(former result)
     *  these tweets must contains the same hash tag so that I get the result
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction  recommendedFollowsBySimilarUsersAndHashtags()
    {
        this.query = "MATCH (u:User{username:\"@majorlyprofound\"})-[:FOLLOWS]->(x:User)<-[:FOLLOWS]-(a:User) WITH a,count(x) as num ORDER BY num DESC LIMIT 10 MATCH(u:User{username:\"@majorlyprofound\"}), a-[:FOLLOWS]->(b:User) where NOT u-[:FOLLOWS]->b and (NOT b.username=\"@majorlyprofound\")   WITH b, count(b) as pos ORDER BY pos DESC LIMIT 10  MATCH (u:User{username:\"@majorlyprofound\"})-[:SENT]->()-[:CONTAINS]->(tag:Hashtag)<-[:CONTAINS]-()<-[:SENT]-b WITH b,count(tag) as ord ORDER BY ord DESC LIMIT 10 return b ";
        return this;
    }

}
