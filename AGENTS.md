# Repository Guidelines

This Kotlin 2.2 / JVM 21 workspace powers a CLI sample app backed by a payment SDK; follow the steps below to add features safely.

## Project Structure & Module Organization
- `app/` is the CLI entry point (`Main.kt`) that simulates a POS payment; pass amount/method/installments via program args.
- `payment-sdk/` holds reusable SDK layers: `api` (contracts), `domain` (client orchestration), `device` (card payload abstractions), and `gateway` (Stripe + sandbox integrations).
- `docs/` stores architecture diagrams describing layer boundaries and POS flows—scan them before changing cross-layer contracts.

## Build, Test, and Development Commands
- `./gradlew build` compiles both modules and verifies dependency locking.
- `./gradlew :app:run --args="1000 CREDIT 1"` runs the CLI against the sandbox flow with sample inputs.
- `./gradlew :payment-sdk:test` executes the Kotlin test suite; run it before pushing changes touching SDK logic.

## Coding Style & Naming Conventions
- Stick to idiomatic Kotlin: 4-space indents, trailing commas where helpful, and expression-bodied functions for short mappers (see `PaymentStatusMapper`).
- Keep classes/package names under `com.eliascoelho911` in PascalCase, functions/properties camelCase, and constants UPPER_SNAKE_CASE.
- Coroutines Flow APIs are the default async primitive; expose suspend façades instead of callbacks.

## Testing Guidelines
- Place unit tests under `payment-sdk/src/test/kotlin` mirroring the source package, e.g., `domain/PaymentClientImplTest`.
- Prefer deterministic fake devices/gateways to exercise approval, decline, and error states; mock coroutines with runTest.
- Failing branches must have companion tests; aim for coverage on each PaymentStatus branch before requesting review.

## Commit & Pull Request Guidelines
- Follow the existing history (`feat: add Stripe gateway integration`, `docs: add architecture diagrams`) and use `type: summary` Conventional Commit prefixes when possible.
- Each PR should outline the scenario, commands executed (build + tests), and link any relevant diagram updates or issues.

## Security & Configuration Tips
- Stripe integration pulls secrets from `STRIPE_SECRET_KEY` and `STRIPE_DEFAULT_CUSTOMER_ID`; set them via env vars or JVM properties and never hard-code them.
- Do not commit generated `build/` artifacts or local `.env` files; rely on Gradle properties if reproducible config is needed.
