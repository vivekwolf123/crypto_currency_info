package com.client.android.feature_base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class BaseViewModelTest {

    @Mock
    lateinit var mockReducer: Reducer<TestState, TestEffect, TestEvent>

    private lateinit var viewModel: BaseViewModel<TestState, TestEvent, TestEffect>

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = object :
            BaseViewModel<TestState, TestEvent, TestEffect>(TestState.Initial, mockReducer) {}
    }

    @Test
    fun `given state and event when sendEvent is triggered then state is updated and event is emitted`() = runTest {
        val initialState = TestState.Initial
        val event = TestEvent.SomeEvent
        val expectedState = TestState.Updated
        val expectedEffect = TestEffect.SomeEffect

        Mockito.`when`(mockReducer.reduce(initialState, event))
            .thenReturn(Pair(expectedState, expectedEffect))

        viewModel.sendEvent(event)

        val state = viewModel.state.first()
        assertEquals(expectedState, state)

        val effect = viewModel.effect.first()
        assertEquals(expectedEffect, effect)

        Mockito.verify(mockReducer).reduce(initialState, event)
    }
}

sealed class TestState : Reducer.ViewState {
    data object Initial : TestState()
    data object Updated : TestState()
}

sealed class TestEvent : Reducer.ViewEvent {
    data object SomeEvent : TestEvent()
}

sealed class TestEffect : Reducer.ViewEffect {
    data object SomeEffect : TestEffect()
}
