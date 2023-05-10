package leetcode;

import java.util.*;

/**
 * 355. Design Twitter
 * Medium
 * ----------------
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed.
 *
 * Implement the Twitter class:
 * Twitter() Initializes your twitter object.
 * void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId. Each call to this function will be made with a unique tweetId.
 * List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user themself. Tweets must be ordered from most recent to least recent.
 * void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId.
 * void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId.
 *
 * Example 1:
 * Input
 * ["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
 * [[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
 * Output
 * [null, null, [5], null, null, [6, 5], null, [5]]
 * Explanation
 * Twitter twitter = new Twitter();
 * twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
 * twitter.follow(1, 2);    // User 1 follows user 2.
 * twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
 * twitter.unfollow(1, 2);  // User 1 unfollows user 2.
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.
 *
 * Constraints:
 * 1 <= userId, followerId, followeeId <= 500
 * 0 <= tweetId <= 10^4
 * All the tweets have unique IDs.
 * At most 3 * 10^4 calls will be made to postTweet, getNewsFeed, follow, and unfollow.
 */
public class Design_Twitter_355 {
    /**
     * 采用面向对象的思路，为User和Tweet分表创建对象
     *
     * 验证通过：
     * Runtime: 9 ms, faster than 68.32% of Java online submissions for Design Twitter.
     * Memory Usage: 37.4 MB, less than 42.39% of Java online submissions for Design Twitter.
     */
    class Twitter {

        Map<Integer, TUser> userList;//用户列表
        int timestamp = 1;//单调递增的时间戳

        /** Initialize your data structure here. */
        public Twitter() {
            userList = new HashMap<>();
        }

        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            initUser(userId);
            TUser user = userList.get(userId);
            Tweet tweet = new Tweet(userId, tweetId, timestamp++);
            user.tweets.add(tweet);
            user.feeds.add(tweet);
            //通知follower，feeds处理
            for (int i : user.fans) {
                userList.get(i).feeds.add(tweet);
            }
        }

        /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
        public List<Integer> getNewsFeed(int userId) {
            initUser(userId);
            List<Integer> ret = new ArrayList<>();
            for (int i = userList.get(userId).feeds.size() - 1; i >= 0; i--) {
                if (ret.size() >= 10) {
                    break;
                }
                ret.add(userList.get(userId).feeds.get(i).tweetId);
            }
            return ret;
        }

        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            initUser(followerId);
            initUser(followeeId);
            //round 2 防止重复follow
            if (userList.get(followerId).followedByMe.contains(followeeId)) {
                return;
            }
            userList.get(followerId).followedByMe.add(followeeId);
            userList.get(followeeId).fans.add(followerId);
            //feeds处理,把followeeId的tweet添加到followerId的feeds中
            userList.get(followerId).feeds.addAll(userList.get(followeeId).tweets);
            //根据tweet的发布时间排序
            userList.get(followerId).feeds.sort(new Comparator<Tweet>() {
                @Override
                public int compare(Tweet o1, Tweet o2) {
                    if (o1.timestamp > o2.timestamp) {
                        return 1;
                    } else if (o1.timestamp < o2.timestamp) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            initUser(followerId);
            initUser(followeeId);
            //round 2 防止重复unfollow
            if (!userList.get(followerId).followedByMe.contains(followeeId)) {
                return;
            }
            userList.get(followerId).followedByMe.remove(new Integer(followeeId));//round 2 ，remove()的输入
            userList.get(followeeId).fans.remove(new Integer(followerId));
            //round 2 可以替换上面的代码
//            userList.get(followerId).feeds.removeAll(userList.get(followeeId).tweets);

            //feeds处理，把followeeId的tweet从followerId的Feeds中移除
            for (Tweet t : userList.get(followeeId).tweets) {
                userList.get(followerId).feeds.remove(t);
            }
        }

        /**
         * 初始化用户
         * @param id
         */
        private void initUser(int id) {
            userList.computeIfAbsent(id, v -> new TUser(id));
        }
    }

    class TUser {
        int userId;
        List<Integer> fans = new ArrayList<>();//关注我的
        List<Integer> followedByMe = new ArrayList<>();//我关注的
        List<Tweet> tweets = new ArrayList<>();//自己的tweet
        List<Tweet> feeds = new ArrayList<>();//自己的Feeds

        public TUser(int id) {
            userId = id;
        }
    }

    class Tweet {
        int userId;
        int tweetId;
        int timestamp;

        public Tweet(int userId, int tweetId, int timestamp) {
            this.userId = userId;
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
}
