# playlist-maker-android-SabitovRatmir
Учебный проект по созданию мобильного приложения

### Список изменений

#### Спринт 6 (v6.0)
- Реализован SearchViewModel с StateFlow для управления состояниями поиска
- Создан sealed class SearchState с состояниями: Initial, Searching, Success, Fail
- Реализован SearchScreen composable с полем поиска и обработкой состояний
- Добавлен TrackListItem composable для отображения элементов треков

#### Спринт 5 (v5.0)
- Реализована Clean Architecture с разделением на Data, Domain, UI слои
- Создан эмулятор сервера (Storage) с данными треков и функцией поиска
- Реализован Data слой: TrackDto, TracksSearchRequest, TracksSearchResponse
- Добавлен RetrofitNetworkClient для эмуляции сетевых запросов
- Создан Domain слой с интерфейсами NetworkClient и TracksRepository
- Реализован TracksRepositoryImpl с конвертацией данных и поиском треков

#### Спринт 4 (v4.0)
- Реализован экран поиска с поисковой строкой и логикой очистки
- Реализован экран настроек с функционалом шеринга, email и соглашения
- Создана система навигации с использованием NavHost и NavController

#### Спринт 3 (v3.0)
- Реализован главный экран приложения по дизайн-макету Figma
- Созданы активности SettingsActivity и SearchActivity
- Реализована навигация между экранами
- Обновлен AndroidManifest.xml с новыми активностями
- Добавлены обработчики нажатий для кнопок навигации