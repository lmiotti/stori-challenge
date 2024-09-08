package com.stori.challenge.presentation.ui.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.stori.challenge.R
import com.stori.challenge.domain.model.Movement
import com.stori.challenge.presentation.ui.component.BalanceCard
import com.stori.challenge.presentation.ui.component.MovementsList
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.HomeIntent
import com.stori.challenge.presentation.ui.state.HomeState
import com.stori.challenge.presentation.ui.viewmodel.HomeViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    goToLoginScreen: () -> Unit,
    goToMovementDetail: (Movement) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToLoginScreen.collectLatest {
                goToLoginScreen()
            }
        }
    }
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.showError.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    val handleIntent = { intent: HomeIntent ->
        if (intent is HomeIntent.OnMovementClicked) {
            goToMovementDetail(intent.movement)
        } else {
            viewModel.handleIntent(intent)
        }
    }

    Scaffold(
        topBar = {
            StoriTopBar(
                showAction = true,
                onActionClicked = { handleIntent(HomeIntent.OnSignOutClicked) },
                isHome = true
            )
        }
    ) {
        if (state.isLoading) {
            HomeScreenLoading(it)
        } else {
            HomeScreenContent(it, state, handleIntent)
        }
    }
}

@Composable
fun HomeScreenLoading(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = dimensionResource(id = R.dimen.padding_xl))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xl))
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.home_loading_height))
                    .padding(dimensionResource(id = R.dimen.padding_m))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius)))
                    .shimmer()
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

@Composable
fun HomeNoMovementsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.home_no_movements_size)),
            painter = painterResource(id = R.drawable.pic_no_movements),
            contentDescription = "No movements"
        )
        Text(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_xl))
                .fillMaxWidth(),
            text = "There are no movements at the moment",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    state: HomeState,
    handleIntent: (HomeIntent) -> Unit
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = dimensionResource(id = R.dimen.padding_xl))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xl))
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (state.name.isNotEmpty())
            Text(
                text = String.format(stringResource(id = R.string.home_hi_format), state.name),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )


        if (state.movements.isEmpty()) {
            HomeNoMovementsScreen()
        } else {
            BalanceCard(balance = state.balance)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xxl)))
            MovementsList(
                movements = state.movements,
                onMovementClicked = { handleIntent(HomeIntent.OnMovementClicked(it)) }
            )
        }
    }
}