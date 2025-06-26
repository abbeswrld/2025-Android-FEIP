package co.feip.fefu2025.presentation.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import co.feip.fefu2025.CustomFlexBoxLayout
import co.feip.fefu2025.LanguageView
import co.feip.fefu2025.R
import co.feip.fefu2025.data.source.RepositoryCard
import co.feip.fefu2025.presentation.component.LanguageDistributionBar

@Composable
fun RepositoryCardContent(card: RepositoryCard, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = card.iconResId),
                    contentDescription = "User",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = card.username,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = card.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Languages:",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            LanguageDistributionBar(
                languages = card.languages.map { it.name to it.percentage },
                colors = card.languages.map { it.color },
            )

            Spacer(modifier = Modifier.height(8.dp))

            AndroidView(factory = { context ->
                CustomFlexBoxLayout(context).apply {
                    for (lang in card.languages) {
                        val langView = LanguageView(context).apply {
                            setLanguageName(lang.name)
                            setCircleColor(lang.color)
                            setPercentage(lang.percentage)
                        }
                        addView(langView)
                    }
                }
            })

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Created on: ${card.createdDate}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Stars",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = card.stars,
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.fork),
                        contentDescription = "Forks",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = card.forks,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}