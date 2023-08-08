package com.sam.monthlytips

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sam.monthlytips.data.TipData
import com.sam.monthlytips.model.Tip
import com.sam.monthlytips.ui.theme.MonthlyTipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonthlyTipsTheme(dynamicColor = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipsApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsApp() {
    val tips: List<Tip> = TipData().loadTips()
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp


    Scaffold(topBar = { HomeTopAppbar() }) { it ->
        if (screenWidth < 600.dp) {
            LazyColumn(
                contentPadding = it,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(tips) {
                    CardListItem(tip = it, modifier = Modifier.padding(4.dp))
                }
            }
        } else LazyVerticalGrid(
            contentPadding = it,
            columns = GridCells.Adaptive(minSize = 300.dp)
        ) {
            items(tips) {
                CardListItem(tip = it, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppbar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.home_appbar_title))
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(modifier: Modifier, tip: Tip) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        onClick = { expanded = !expanded },
        modifier = modifier.animateContentSize(
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
    ) {
        Column {
            CardHeader(
                day = tip.dayNumber,
                title = tip.title,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
            )
            CardBody(imageID = tip.imageID, description = tip.description, expanded = expanded)
        }
    }
}

@Composable
fun CardHeader(day: Int, title: Int, modifier: Modifier) {
    Row {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.day, day),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(text = stringResource(id = title), style = MaterialTheme.typography.titleMedium)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CardHeaderPrev() {
    val tipData = Tip(
        title = R.string.title_1,
        description = R.string.description_1,
        dayNumber = 1,
        imageID = R.drawable.img1
    )
    CardHeader(tipData.dayNumber, title = tipData.title, modifier = Modifier.padding(4.dp))
}


@Composable
fun CardBody(
    imageID: Int,
    description: Int,
    expanded: Boolean,
) {
    if (expanded)
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = description)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = imageID),
                contentDescription = null
            )
        }
}

@Preview(showBackground = true)
@Composable
fun CardBodyPrev() {
    val tipData = Tip(
        title = R.string.title_1,
        description = R.string.description_1,
        dayNumber = 1,
        imageID = R.drawable.img1
    )

    CardBody(imageID = tipData.imageID, description = tipData.description, expanded = true)
}