/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beomjo.coroutine.codelab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beomjo.coroutine.codelab.main.MainViewModel
import com.beomjo.coroutine.codelab.main.TitleRepository
import com.beomjo.coroutine.codelab.utils.MainCoroutineScopeRule
import com.beomjo.coroutine.codelab.utils.getValueForTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var subject: MainViewModel

    @Before
    fun setup() {
        subject = MainViewModel(
            TitleRepository(
                MainNetworkFake("OK"),
                TitleDaoFake("initial")
            )
        )
    }

    @Test
    fun whenMainClicked_updatesTaps() {
        subject.onMainViewClicked()
        assertThat(subject.taps.getValueForTest(), equalTo("0 taps"))

        // 가상 시간을 사용하여 코루틴 실행 제어
        // advanceTimeBy을 사용하여 디스패처가 1초 후에 재개하도록 예약된 코루틴을 즉시 실행하도록 호출
        coroutineScope.advanceTimeBy(1_000)

        assertThat(subject.taps.getValueForTest(), equalTo("1 taps"))
    }
}