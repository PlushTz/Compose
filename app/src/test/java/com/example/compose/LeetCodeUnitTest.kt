package com.example.compose

import org.junit.Assert.assertArrayEquals
import org.junit.Test

/**
 * Desc:
 * @author lijt
 * Created on 2026/5/7
 * Email: lijt@eetrust.com
 */
class LeetCodeUnitTest {
    @Test
    fun leetCode88Test() {
        val nums1 = intArrayOf(1, 2, 3, 4, 7, 9)
        val m = 3
        val nums2 = intArrayOf(2, 5, 6)
        val n = 3
        merge(nums1, m, nums2, n)
        assertArrayEquals(intArrayOf(1, 2, 2, 3, 5, 6), nums1)

        // 在 Kotlin 中，Array 直接调用 toString() 会打印内存地址
        // 需要使用 contentToString() 来输出数组的具体内容
        println("leetCode88Test 结果: ${nums1.contentToString()}")
    }

    /**
     * 88. 合并两个有序数组
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，
     * 另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     *
     * 时间复杂度: O(m + n)
     * 空间复杂度: O(1)
     */
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        var p1 = m - 1
        var p2 = n - 1
        var p = m + n - 1

        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p--] = nums1[p1--]
            } else {
                nums1[p--] = nums2[p2--]
            }
        }
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        for (i in nums.indices) {
            val complement = target - nums[i]
            if (map.containsKey(complement)) {
                return intArrayOf(map[complement]!!, i)
            }
            map[nums[i]] = i
        }
        return intArrayOf()
    }
}
