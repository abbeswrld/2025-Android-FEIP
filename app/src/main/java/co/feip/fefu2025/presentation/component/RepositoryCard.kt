package co.feip.fefu2025.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import co.feip.fefu2025.R
import co.feip.fefu2025.data.source.Repository

@Composable
fun RepositoryCard(repository: Repository, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),

        ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(repository.iconResId),
                    contentDescription = "",
                    modifier = modifier
                        .size(24.dp)
                        .padding(top = 3.dp)
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = repository.username,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = repository.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(top = 8.dp)
            )

            Spacer(modifier = modifier.weight(1f))

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Stars",
                        modifier = modifier.size(22.dp).padding(bottom = 3.dp)
                    )
                    Text(text = repository.stars, fontSize = 15.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.fork),
                        contentDescription = "Forks",
                        modifier = modifier.size(22.dp)
                    )
                    Text(text = repository.forks, fontSize = 15.sp)
                }
            }
        }
    }
}