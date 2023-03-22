package CCProgrammingContest.V2022Question3;

/**
 * 问题 C: 分布式资源管理器
 *
 * 题目描述
 * 集群中有 n 台服务器，编号从 1 到 n，每台服务器拥有 cpu 和 内存资源，由资源管理器对集群资源进行统一管理和分配。
 * 现在要部署 m 个应用，编号从 1 到 m，每个应用占用一定的cpu和内存资源，应用部署规则如下：
 * 1. 单台服务器的可用cpu和内存资源都不少于应用所需资源时，才可以部署
 * 2. 应用名称相同的不能部署在同一台服务器
 * 3. 有多台服务器满足部署条件时，选择权重值最大的服务器，权重算法为
 *    W = 可用CPU数量 x 2 + 可用内存数量
 * 4. 如果有多个权重值相同的服务器可用，则选择编号最小的服务器。
 *
 * 请按顺序输出当前集群所有服务器的可用cpu、可用内存数量，以及未能正常部署的应用编号。
 *
 * 输入
 * 共两行。
 * 第一行表示服务器资源情况。例如输入 '2 16 32 16 32'，输入项以空格分开，第一项代表集群中服务器的数量 n，后面每连续 2 项分别代表每台服务器的可用 cpu 和可用内存资源，服务器按编号从小到大的顺序排列。
 * 第二行表示需要部署的应用，例如输入 '6 4 8 app1 4 8 app2 16 32 app3 4 8 app1 4 8 app1 4 8 app3'，输入项以空格分开，第一项代表应用的数量 m，后面每连续 3 项分别代表每个应用所需的cpu、内存资源和应用名称，应用按编号从小到大的顺序排列。
 * n 和 m 都不超过 100。
 *
 * 输出
 * 共一行，按顺序输出应用部署后每台服务器剩余的可用cpu、内存资源，再按顺序输出没能正常部署的应用编号，都以空格分隔。
 *
 * 样例输入 Copy
 * 2 16 32 16 32
 * 6 4 8 app1 4 8 app2 16 32 app3 4 8 app1 4 8 app1 4 8 app3
 *
 * 样例输出 Copy
 * 8 16 8 16 3 5
 *
 * 提示
 * 1. 输入 '2 16 32 16 32'
 *    两台服务器可用资源情况为 S1{16,32}, S2{16,32}
 * 2. 输入 '6 4 8 app1 4 8 app2 16 32 app3 4 8 app1 4 8 app1 4 8 app3'
 *    共需要部署6个应用
 * 3. 部署第一个应用后 A1 {4, 8, "app1"}, app1 部署在S1
 *    S1{12,24}, S2{16,32}
 * 4. 部署第二个应用后 A2 {4, 8, "app2"}, app2 部署在 S2
 *    S1{12,24}, S2{12,24}
 * 5. 部署第三个应用时，A3 {16, 32, "app3"}, 不能部署
 *    S1{12,24}, S2{12,24}
 * 6. 部署第四个应用, A4 {4, 8, "app1"}, app1 已经在S1部署过，此时应该部署在 S2
 *    S1{12,24}, S2{8,16}
 * 7. 部署第五个应用时, A5 {4, 8, "app1"} app1 已经在S1、S2 部署过，此时不能部署
 *    S1{12,24}, S2{8,16}
 * 8. 部署第六个应用时，A6 {4, 8, "app3"} app3 部署在S1
 *    S1{8,16}, S2{8,16}
 */
public class Main {
}