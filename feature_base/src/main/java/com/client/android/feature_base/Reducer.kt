package com.client.android.feature_base

interface Reducer<State : Reducer.ViewState, Effect : Reducer.ViewEffect, Event : Reducer.ViewEvent> {

    interface ViewState

    interface ViewEffect

    interface ViewEvent

    fun reduce(state: State, event: Event): Pair<State, Effect?>
}
