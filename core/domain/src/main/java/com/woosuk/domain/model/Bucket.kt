package com.woosuk.domain.model

data class Bucket(
    val category: BucketCategory,
    val ageRange: AgeRange,
    val title: String,
    val description: String? = null,
) {
    companion object {
        fun mock(): Bucket =
            Bucket(
                category = BucketCategory.Health,
                ageRange = AgeRange.Twenties,
                title = "미국 여행 가기",
                description = "뉴욕가서 릴스 찍기",
            )
    }
}

class BucketList(
    initValue: List<Bucket>,
) {
    private val value = initValue.toList()

    fun getBucketListByCategory(bucketCategory: BucketCategory) = value.filter { it.category == bucketCategory }

    companion object {
        fun mock(): BucketList =
            BucketList(
                listOf(
                    Bucket(
                        category = BucketCategory.Work,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Health,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Learning,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Travel,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Unspecified,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Work,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Health,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Learning,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Travel,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Unspecified,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                    Bucket(
                        category = BucketCategory.Work,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = null,
                    ),
                ),
            )
    }
}
