package uk.ac.ncl.csc8101;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Class consisting of simple methods to set the basic Cypher
 * query strings to be executed.
 *
 * @author Hugo Firth <h.firth @ ncl.ac.uk>
 * @since 2014-01-23
 * @see CypherInteraction
 */
public class BasicCypherInteraction extends CypherInteraction {

    public BasicCypherInteraction(GraphDatabaseService db) {
        super(db);
    }

    /**
     * Description: Return those users who retweeted tweets of user @majorlyprofound
     * which do not contain a hashtag
     *
     * Student Rationale:
     * First I find the user who send tweet which retweets the tweet send by @majorlyprofound
     * and at "where" I give a rule the tweets tetweeted by user do not contain hashtag. At last,
     * I get the users.
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction usersRetweetingUserNoHashtag()
    {
        this.query = "MATCH     (u:User)-[:SENT]->(t:Tweet)<-[RETWEETS]-()<-[:SENT]-(a:User) where NOT t- [:CONTAINS]->() and  u.username = \"@majorlyprofound\" return a";
        return this;
    }

    /**
     * Description: Return those users who retweeted tweets of user @majorlyprofound
     * which do not contain a hashtag and who are not following user @majorlyprofound
     *
     * Student Rationale:
     * The only difference with the former one is to add a rule : users are not following @majorlyprofound
     *
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction usersRetweetingUserNoHashtagNoFollow()
    {
        this.query = "MATCH     (u:User)-[:SENT]->(t:Tweet)<-[RETWEETS]-()<-[:SENT]-(a:User) where NOT t- [:CONTAINS]->() and  u.username = \"@majorlyprofound\" and NOT a-[:FOLLOWS]->u return a";
        return this;
    }

    /**
     * Description: Return top 10 most followed users in descending order
     *
     * Student Rationale:
     * First get the users who are followed by most and use "with" to count the number of
     * followers then order the number as descending order. At last return the users limit 10,
     * so I get the top 10
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction mostFollowedUsers()
    {
        this.query = "MATCH (u:User)-[:FOLLOWS]->(a:User)WITH a,count(u) as num ORDER BY num DESC LIMIT 10 return a ";
        return this;
    }

    /**
     * Description: Return max number of hops between user @majorlyprofound and any
     * other user (Friend of a Friend).
     *
     * Hint: Neo4j has functions.
     * Note: you are returning a number, not a node here.
     *
     * Student Rationale:
     * It is possible to calculate hops only when I just focus on the relationship of following.
     * So I use p represent the shortestPath then i get the hops. At last just return the max of it.
     *
     *
     *
     * @return the Cypher Interaction to be run()
     */
    public CypherInteraction maxHopsFromUser()
    {
        this.query = "MATCH p=shortestPath((u:User{username:\"@majorlyprofound\"})-[:FOLLOWS*]->(a:User)) WITH max(length(p)) as l  RETURN l";
        return this;
    }
}
