# Repository Guidelines

## Project Structure & Module Organization
- `app/`: Jetpack Compose UI, navigation, and sales/history flows. Keep code under `app/src/main/java`, resources in `app/src/main/res`. Consume only the SDK public API (no direct Room/database access).
- `payment-sdk/`: Internal SDK with payment entrypoint, coroutine/Flow APIs, fake gateway integration, and Room storage. Persist transactions here; expose only domain models/events to `app`.
- `gradle/libs.versions.toml`: Source of truth for versions/aliases. Add libraries/plugins here before using them.

## Build, Test, and Development Commands
- `./gradlew assembleDebug` — assemble the debug APK.
- `./gradlew :payment-sdk:test` — JVM unit tests for SDK rules (gateway outcomes, persistence, state transitions).
- `./gradlew :app:connectedAndroidTest` — instrumentation/Compose UI tests on a device/emulator.
- `./gradlew :app:installDebug` — deploy the debug build to a connected device.

## Coding Style & Naming Conventions
- Kotlin/Compose: 4-space indent, trailing commas when useful. Use `PascalCase` for classes/composables, `camelCase` for functions/properties, `snake_case` for resources.
- Async APIs should be `suspend` or `Flow`; avoid callbacks. App layer observes `Flow<Event>` from the SDK for payment progress.
- Boundaries: app orchestrates UI; SDK owns gateway logic, card-reading simulation, and Room repositories. Never store full PAN; keep only last 4 digits, brand, and auth code.
- Keep the SDK surface stable (`PaymentSdk`/`PaymentsClient`, `startPayment`, `getTransactions`, `getTransaction`). Prefer evolving parameter objects over breaking signatures.

## Testing Guidelines
- Frameworks: JUnit for unit tests; AndroidX Compose UI Test/Espresso for instrumentation. Place unit tests in `src/test/java`, UI tests in `src/androidTest/java`, using `*Test`/`*IT` suffixes.
- Cover scenarios: gateway approved/declined/timeout, Room persistence and retrieval, masking rules, and UI flows for approved/declined sales. Prefer deterministic fakes by seeding the gateway simulator.
- Run relevant test commands before pushing; call out any gaps in the PR.

## Commit & Pull Request Guidelines
- Commits: concise, present-tense summaries. Group related changes; optional prefixes (`feat:`, `fix:`, `chore:`) are welcome.
- Pull requests: include motivation, bullet list of changes, test results, and screenshots/gifs for UI updates. Note SDK API changes and migration steps for the app module.

## Architecture Notes
- Target flow: app calls SDK `startPayment(amount, method, installments, metadata?)`, observes events (`CardReading`, `Authorizing`, `Approved`, `Declined`, `Failed`), then shows receipt/history. SDK persists `TransactionEntity` with id, amount (cents), status, brand, last4, authCode, timestamps, optional raw response.
- Gateway is a local fake; support deterministic seeds for approvals/declines/timeouts to keep tests and demos reliable. Keep the app thin; complexity stays inside the SDK.
