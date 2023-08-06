package com.sam.monthlytips

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
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
            MonthlyTipsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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

    Scaffold(topBar = { HomeTopAppbar() }) {
        LazyColumn {
            items(tips) {
                CardListItem(tip = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppbar() {
    CenterAlignedTopAppBar(
            title = {
                Text(text = "30 Days Of Wellness")
            },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListItem(tip: Tip) {
    var expanded by remember { mutableStateOf(false) }

    Card(onClick = { expanded = !expanded }) {
        Column {
            CardHeader(day = tip.dayNumber, title = tip.title)
            CardBody(imageID = tip.imageID, description = tip.description, expanded = expanded)
        }
    }
}

@Composable
fun CardHeader(day: Int, title: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Day $day", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Text(text = stringResource(id = title), style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun CardHeaderPrev() {
    val tipData = Tip(title = R.string.title_1, description = R.string.description_1, dayNumber = 1, imageID = R.drawable.img1)
    CardHeader(tipData.dayNumber, title = tipData.title, modifier = Modifier.padding(4.dp))
}


@Composable
fun CardBody(
        modifier: Modifier = Modifier,
        imageID: Int,
        description: Int,
        expanded: Boolean = false,
) {
    Column(modifier = Modifier
            .padding(4.dp)
            .then(modifier)) {
        Image(modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(), contentScale = ContentScale.Crop, painter = painterResource(id = imageID), contentDescription = null)
        Spacer(modifier = Modifier.height(4.dp))
        if (expanded) Text(text = stringResource(id = description))
    }
}

@Preview(showBackground = true)
@Composable
fun CardBodyPrev() {
    val tipData = Tip(title = R.string.title_1, description = R.string.description_1, dayNumber = 1, imageID = R.drawable.img1)

    CardBody(imageID = tipData.imageID, description = tipData.description, expanded = true)
}