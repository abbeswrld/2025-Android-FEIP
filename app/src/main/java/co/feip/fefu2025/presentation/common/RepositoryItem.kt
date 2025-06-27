package co.feip.fefu2025.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.feip.fefu2025.data.source.Repository
import coil.compose.AsyncImage

@Composable
fun RepositoryItem(
    repo: Repository,
    onStarClick: (Repository) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = repo.avatarUrl,
                contentDescription = "${repo.username} avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(repo.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(repo.username, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(Modifier.height(4.dp))
                Text(
                    repo.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Stars", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(repo.stars, fontSize = 14.sp)
                    Spacer(Modifier.width(16.dp))
                    Text("⑂", fontSize = 16.sp, fontWeight = FontWeight.Bold) // Fork icon
                    Spacer(Modifier.width(4.dp))
                    Text(repo.forks, fontSize = 14.sp)
                }
            }
            IconButton(onClick = { onStarClick(repo) }) {
                Icon(
                    imageVector = if (repo.isStarred) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "Star",
                    tint = if (repo.isStarred) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}