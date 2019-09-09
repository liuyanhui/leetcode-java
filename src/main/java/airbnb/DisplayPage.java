package airbnb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayPage {

    static String findNumber(List<Integer> arr, int k) {
        boolean ret = arr.parallelStream().allMatch(i -> i == k);
        if (ret) {
            return "YES";
        } else {
            return "NO";
        }
    }

    public static void main(String[] args) {

        List<String> input = new ArrayList<>();
        input.add("1,28,300.6,San Francisco");
        input.add("4,5,209.1,San Francisco");
        input.add("20,7,203.4,Oakland");
        input.add("6,8,202.9,San Francisco");
        input.add("6,10,199.8,San Francisco");
        input.add("1,16,190.5,San Francisco");
        input.add("6,29,185.3,San Francisco");
        input.add("7,20,180.0,Oakland");
        input.add("6,21,162.2,San Francisco");
        input.add("2,18,161.7,San Jose");
        input.add("2,30,149.8,San Jose");
        input.add("3,76,146.7,San Francisco");
        input.add("2,14,141.8,San Jose");

        System.out.println("---------paginate0508--------");
        List<String> ret0508 = paginate_20190508(5, new ArrayList<>(input));
        ret0508.stream().forEach(s -> System.out.println(s));
        System.out.println("---------paginate_no_iterator--------");
        List<String> ret_no_iterator = paginate_no_iterator(5, new ArrayList<>(input));
        ret_no_iterator.stream().forEach(s -> System.out.println(s));
        System.out.println("---------pagenate3--------");
        List<String> ret3 = pagenate3(5, new ArrayList<>(input));
        ret3.stream().forEach(s -> System.out.println(s));
        System.out.println("--------paginate---------");
        List<String> ret = paginate(5, new ArrayList<>(input));
        ret.stream().forEach(s -> System.out.println(s));
        System.out.println("---------paginate2--------");
        List<String> ret2 = paginate2(5, input);
        ret2.stream().forEach(s -> System.out.println(s));

    }


    public static List<String> paginate_20190508(int resultsPerPage, List<String> results) {
        //遍历results
        //中间变量exist_id和cur_size和reachEnd
        //if not in exist_id 并且 cur_size<resultsPerPage：加入到结果中，cur_size++
        //if in exist_id 中：跳过该条
        //if in exist_id 并且 reachEnd==true：加入结果中，cur_size++
        //if cur_size==resultsPerPage：重头遍历，cur_size=0
        //if 到达list尾部：重头遍历，reachEnd=true
        /*
         * 时间复杂度O(n*n)，空间复杂度O(n*3)
         * */
        List<String> ret = new ArrayList<>();
        if (results == null || results.size() == 0 || resultsPerPage <= 0) {
            return ret;
        }
        List<String> existIdList = new ArrayList<>();
        boolean reachEnd = false;

        Iterator iterator = results.iterator();
        while (iterator.hasNext()) {
            //iterator.next()时，游标已经后移了。这里跟数组的游标不一样，不用显示控制向后移。
            String item = iterator.next().toString();
            String id = item.split(",")[0];
            if (!existIdList.contains(id) || reachEnd == true) {
                ret.add(item);
                existIdList.add(id);
                iterator.remove();
                reachEnd = false;
            }
            if (existIdList.size() == resultsPerPage) {
                ret.add(" ");
                existIdList.clear();
                iterator = results.iterator();
            }
            if (!iterator.hasNext()) {
                reachEnd = true;
                iterator = results.iterator();
            }

        }

        return ret;
    }

    public static List<String> paginate_no_iterator(int resultsPerPage, List<String> results) {
        /*
         * 时间复杂度O(n)，空间复杂度O(n*2)
         * */
        List<String> ret = new ArrayList<>();
        if (results == null || results.size() == 0 || resultsPerPage <= 0) {
            return ret;
        }
        List<String> existIdList = new ArrayList<>();
        int[] dirtyIndexArray = new int[results.size()];//记录已经输出的记录的index
        boolean reachEnd = false;
        int removedCount = 0;
        int index = 0;

        while (removedCount < results.size()) {
            while (dirtyIndexArray[index] == 1) {
                index++;
            }
            String item = results.get(index);
            String id = item.split(",")[0];
            if (!existIdList.contains(id) || reachEnd) {
                ret.add(item);
                dirtyIndexArray[index] = 1;
                removedCount++;
                existIdList.add(id);
                reachEnd = false;
            }
            index++;

            if (existIdList.size() == resultsPerPage) {
                ret.add(" ");
                index = 0;
                existIdList.clear();
            }

            if (index == results.size()) {
                index = 0;
                reachEnd = true;
            }
        }

        return ret;
    }

    /*
     * Complete the 'paginate' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER resultsPerPage
     *  2. STRING_ARRAY results
     */

    public static List<String> paginate2(int resultsPerPage, List<String> results) {
        // Write your code here
        List<String> ret = new ArrayList<>();
        if (resultsPerPage <= 0 || results == null || results.size() == 0) {
            return ret;
        }

        Iterator iterator = results.iterator();
        List<String> existHostId = new ArrayList<>();
        boolean fresh = false;

        while (iterator.hasNext()) {
            String row = (String) iterator.next();
            String[] itemArray = row.split(",");
            String hostId = itemArray[0];

            if (!existHostId.contains(hostId) || fresh) {
                ret.add(row);
                existHostId.add(hostId);
                iterator.remove();
            }

            if (existHostId.size() == resultsPerPage) {
                existHostId.clear();
                fresh = false;
                if (!results.isEmpty()) {
                    ret.add("");
                }
                iterator = results.iterator();
            }

            if (!iterator.hasNext()) {
                iterator = results.iterator();
                fresh = true;
            }
        }

        return ret;
    }

    public static List<String> paginate(int resultsPerPage, List<String> results) {
        // Write your code here
        List<String> ret = new ArrayList<>();
        if (resultsPerPage <= 0 || results == null || results.size() == 0) {
            return ret;
        }

        Iterator iterator = results.iterator();
        List<String> existHostId = new ArrayList<>();
        boolean fresh = true;

        while (iterator.hasNext()) {
            String row = (String) iterator.next();
            String[] itemArray = row.split(",");
            String hostId = itemArray[0];

            if (!existHostId.contains(hostId) || !fresh) {
                ret.add(row);
                existHostId.add(hostId);
                iterator.remove();
                if (existHostId.size() == resultsPerPage) {
                    ret.add("");
                    existHostId.clear();
                    iterator = results.iterator();
                    fresh = true;
                }
            }

            //iterator回到起点
            if (!iterator.hasNext()) {
                iterator = results.iterator();
                fresh = false;
            }
        }

        return ret;
    }

    public static List<String> pagenate3(int resultsPerPage, List<String> results) {
        if (resultsPerPage <= 0 || results == null || results.size() <= 0) {
            return null;
        }
        List<String> ret = new ArrayList<>();

        List<String> existedHostId = new ArrayList<>();
        Iterator<String> iterator = results.iterator();
        boolean fresh = true;

        while (iterator.hasNext()) {
            String row = iterator.next();
            String hostId = row.split(",")[0];

            if (!existedHostId.contains(hostId) || !fresh) {
                ret.add(row);
                existedHostId.add(hostId);
                iterator.remove();

                if (existedHostId.size() == resultsPerPage) {
                    ret.add("");
                    existedHostId.clear();
                    fresh = true;
                    iterator = results.iterator();
                }
            }

            if (!iterator.hasNext()) {
                iterator = results.iterator();
                fresh = false;
            }

        }

        return ret;
    }
}
