object AppVersions {
    private const val major = 1
    private const val minor = 0
    private const val fix = 0

    const val VERSION_CODE = major * 1_000_000 + minor * 1_000 + fix
    const val VERSION_NAME = "$major.$minor.$fix"
}
