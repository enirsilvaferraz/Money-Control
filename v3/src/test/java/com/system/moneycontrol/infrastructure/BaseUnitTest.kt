package com.system.moneycontrol.infrastructure

import io.mockk.MockKAnnotations
import org.junit.Before

open class BaseUnitTest {

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)
}
