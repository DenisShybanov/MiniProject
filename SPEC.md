# QR-Master - Специфікація

## 1. Огляд проекту

**Назва:** QR-Master  
**Тип:** Android-додаток  
**Призначення:** Універсальний інструмент для зчитування, створення та каталогізації QR-кодів.

## 2. Технологічний стек

| Компонент | Технологія |
|-----------|------------|
| Мова | Kotlin 1.9.x |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |
| Архітектура | MVVM + Clean Architecture |
| DI | Hilt |
| База даних | Room |
| Камера | CameraX + ML Kit Barcode Scanning |
| Генерація QR | ZXing Core |
| UI | Material Design 3 |
| Навігація | Jetpack Navigation + BottomNavigationView |

## 3. UI/UX Специфікація

### 3.1 Навігація
- **BottomNavigationView** з 3-ма вкладками:
  - Сканер (іконка: qr_code_scanner)
  - Генератор (іконка: qr_code_2)
  - Історія (іконка: history)

### 3.2 Кольорова палітра
| Колір | Значення | Використання |
|-------|----------|--------------|
| Primary | #1976D2 | Toolbar, кнопки |
| Primary Variant | #0D47A1 | Статус бар |
| Secondary | #FF6F00 | Акценти, FAB |
| Background | #FAFAFA | Фон |
| Surface | #FFFFFF | Картки |
| Error | #B00020 | Помилки |

### 3.3 Екрани

#### ScannerFragment
- Повноекранний PreviewView камери
- Кнопка ліхтарика (toggle) — FAB у правому нижньому куті
- Графічний оверлей з рамкою сканування (центрований прямокутник)
- Автоматичне сканування та збереження до БД

#### GeneratorFragment
- Поле введення (TextInputLayout)
- Spinner вибору типу: URL, TEXT, WIFI, VCARD
- Кнопка "Згенерувати"
- ImageView для відображення QR-коду
- Кнопка "Зберегти" для збереження до історії

#### HistoryFragment
- RecyclerView з CardView
- Поле пошуку (SearchView)
- Фільтри за типом (Chips)
- Swipe-to-delete для видалення

#### DetailsActivity
- Large QR-код (centered)
- TextView з вмістом
- Кнопки: Поділитися, Відкрити, Копіювати

## 4. База даних (Room)

### QRCodeEntity
| Поле | Тип | Опис |
|------|-----|------|
| id | Long (PK) | Auto-generate |
| content | String | Текст/URL |
| type | String | URL, TEXT, WIFI, VCARD |
| timestamp | Long | Час створення |
| isGenerated | Boolean | true — створений, false — відсканований |
| title | String | Користувацька назва |

## 5. Архітектура

```
app/
├── data/
│   ├── local/
│   │   ├── QRCodeDao
│   │   ├── QRCodeDatabase
│   │   └── QRCodeEntity
│   └── repository/
│       └── QRCodeRepositoryImpl
├── di/
│   └── AppModule (Hilt)
├── domain/
│   ├── model/
│   │   └── QRCode
│   ├── repository/
│   │   └── QRCodeRepository
│   └── usecase/
├── ui/
│   ├── scanner/
│   ├── generator/
│   ├── history/
│   └── details/
└── util/
```

## 6. Зовнішні залежності

```gradle
// CameraX
implementation 'androidx.camera:camera-core:1.3.0'
implementation 'androidx.camera:camera-camera2:1.3.0'
implementation 'androidx.camera:camera-lifecycle:1.3.0'
implementation 'androidx.camera:camera-view:1.3.0'

// ML Kit Barcode
implementation 'com.google.mlkit:barcode-scanning:17.2.0'

// ZXing
implementation 'com.google.zxing:core:3.5.2'

// Room
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'

// Hilt
implementation 'com.google.dagger:hilt-android:2.48.1'
annotationProcessor 'com.google.dagger:hilt-android-compiler:2.48.1'

// Navigation
implementation 'androidx.navigation:navigation-fragment-ktx:2.7.5'
implementation 'androidx.navigation:navigation-ui-ktx:2.7.5'
```

## 7. Адаптивність

- **res/layout/** — портретна орієнтація
- **res/layout-land/** — ландшафтна орієнтація (Generator: горизонтальний Layout)