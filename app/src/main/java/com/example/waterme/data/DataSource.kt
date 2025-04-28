package com.example.waterme.data

import com.example.waterme.R
import com.example.waterme.model.Plant

object DataSource {
    val plants = listOf(
        Plant(
            name = R.string.xuong_rong_bong,
            schedule = R.string.monthly,
            type = R.string.succulent,
            description = R.string.durable_desert_cactus
        ),
        Plant(
            name = R.string.cay_phat_loc,
            schedule = R.string.weekly,
            type = R.string.houseplant,
            description = R.string.good_luck_bamboo
        ),
        Plant(
            name = R.string.hoa_hong,
            schedule = R.string.daily,
            type = R.string.flower,
            description = R.string.luxurious_flower
        ),
        Plant(
            name = R.string.cay_bang_singapore,
            schedule = R.string.weekly,
            type = R.string.houseplant,
            description = R.string.large_leaf_tree
        ),
        Plant(
            name = R.string.cay_duoi_con_ngua,
            schedule = R.string.weekly,
            type = R.string.ornamental,
            description = R.string.exotic_indoor_plant
        ),
        Plant(
            name = R.string.cay_nha_dam,
            schedule = R.string.weekly,
            type = R.string.succulent,
            description = R.string.aloe_vera_care
        )
    )
}