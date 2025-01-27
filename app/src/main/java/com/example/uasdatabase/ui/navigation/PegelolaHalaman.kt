package com.example.uasdatabase.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uasdatabase.ui.view.DestinasiAwal
import com.example.uasdatabase.ui.view.HomeAwal
import com.example.uasdatabase.ui.view.acara.DestinasiDetailAcara
import com.example.uasdatabase.ui.view.acara.DestinasiEntryAcara
import com.example.uasdatabase.ui.view.acara.DestinasiHomeAcara
import com.example.uasdatabase.ui.view.acara.DestinasiUpdateAcara
import com.example.uasdatabase.ui.view.acara.DetailAcaraScreen
import com.example.uasdatabase.ui.view.acara.EntryAcaraScreen
import com.example.uasdatabase.ui.view.acara.HomeScreenAcara
import com.example.uasdatabase.ui.view.acara.UpdateAcaraScreen
import com.example.uasdatabase.ui.view.klien.DestinasiDetailKlien
import com.example.uasdatabase.ui.view.klien.DestinasiEntryKlien
import com.example.uasdatabase.ui.view.klien.DestinasiHomeKlien
import com.example.uasdatabase.ui.view.klien.DestinasiUpdateKlien
import com.example.uasdatabase.ui.view.klien.DetailKlienScreen
import com.example.uasdatabase.ui.view.klien.EntryKlienScreen
import com.example.uasdatabase.ui.view.klien.HomeScreenKlien
import com.example.uasdatabase.ui.view.klien.UpdateKlienScreen
import com.example.uasdatabase.ui.view.lokasi.DestinasiDetailLokasi
import com.example.uasdatabase.ui.view.lokasi.DestinasiEntryLokasi
import com.example.uasdatabase.ui.view.lokasi.DestinasiHomeLokasi
import com.example.uasdatabase.ui.view.lokasi.DestinasiUpdateLokasi
import com.example.uasdatabase.ui.view.lokasi.DetailLokasiScreen
import com.example.uasdatabase.ui.view.lokasi.EntryLokasiScreen
import com.example.uasdatabase.ui.view.lokasi.HomeScreenLokasi
import com.example.uasdatabase.ui.view.lokasi.UpdateLokasiScreen
import com.example.uasdatabase.ui.view.vendor.DestinasiDetailVendor
import com.example.uasdatabase.ui.view.vendor.DestinasiEntryVendor
import com.example.uasdatabase.ui.view.vendor.DestinasiHomeVendor
import com.example.uasdatabase.ui.view.vendor.DestinasiUpdateVendor
import com.example.uasdatabase.ui.view.vendor.DetailVendorScreen
import com.example.uasdatabase.ui.view.vendor.EntryVendorScreen
import com.example.uasdatabase.ui.view.vendor.HomeScreenVendor
import com.example.uasdatabase.ui.view.vendor.UpdateVendorScreen


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAcara.route
    ) {
        composable(
            route = DestinasiAwal.route
        ) {
            HomeAwal(
                onKlien = { navController.navigate(DestinasiHomeKlien.route) },
                onVendor = { navController.navigate(DestinasiHomeVendor.route) },
                onLokasi = { navController.navigate(DestinasiHomeLokasi.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        //  Klien
        composable(
            route = DestinasiHomeKlien.route
        ) {
            HomeScreenKlien(
                navigateToItemEntryKlien = { navController.navigate(DestinasiEntryKlien.route) },
                navigateBack = { navController.popBackStack() },
                navigateToItemDetailKlien = { klienId ->
                    navController.navigate("${DestinasiDetailKlien.route}/$klienId")
                },
                navigateToEditKlien = { id_klien ->
                    navController.navigate("${DestinasiUpdateKlien.route}/$id_klien")
                }
            )
        }

        composable(DestinasiEntryKlien.route) {
            EntryKlienScreen(navigateBack = {
                navController.navigate(DestinasiHomeKlien.route) {
                    popUpTo(DestinasiHomeKlien.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiDetailKlien.routeWithArg
        ) { backStackEntry ->
            val klienId =
                backStackEntry.arguments?.getString(DestinasiDetailKlien.ID_KLIEN)?.toInt()
                    ?: return@composable
            DetailKlienScreen(
                onBackClick = { navController.popBackStack() },
                idKlien = klienId
            )
        }

        composable(
            route = DestinasiUpdateKlien.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKlien.ID_KLIEN) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idKlien = backStackEntry.arguments?.getInt(DestinasiUpdateKlien.ID_KLIEN)
            idKlien?.let {
                UpdateKlienScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeKlien.route) {
                            popUpTo(DestinasiHomeKlien.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }


        // Lokasi

        composable(
            route = DestinasiHomeLokasi.route
        ) {
            HomeScreenLokasi(
                navigateToItemEntryLokasi = { navController.navigate(DestinasiEntryLokasi.route) },
                navigateBack = { navController.popBackStack() },
                onDetailLokasi = { id_lokasi ->
                    navController.navigate("${DestinasiDetailLokasi.route}/$id_lokasi")
                },
                navigateToEditLokasi = { id_lokasi ->
                    navController.navigate("${DestinasiUpdateLokasi.route}/$id_lokasi")
                }
            )
        }

        composable(
            route = DestinasiUpdateLokasi.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateLokasi.ID_LOKASI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idLokasi = backStackEntry.arguments?.getInt(DestinasiUpdateLokasi.ID_LOKASI)
            idLokasi?.let {
                UpdateLokasiScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeLokasi.route) {
                            popUpTo(DestinasiHomeLokasi.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }


        composable(DestinasiEntryLokasi.route) {
            EntryLokasiScreen(navigateBack = {
                navController.navigate(DestinasiHomeLokasi.route) {
                    popUpTo(DestinasiHomeLokasi.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = DestinasiDetailLokasi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailLokasi.ID_LOKASI) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idLokasi = backStackEntry.arguments?.getInt(DestinasiDetailLokasi.ID_LOKASI)
            idLokasi?.let {
                DetailLokasiScreen(
                    idLokasi = idLokasi,
                    onBackClick = { navController.popBackStack() },
                )
            }
        }

        // Vendor

        composable(
            route = DestinasiHomeVendor.route
        ) {
            HomeScreenVendor(
                navigateToItemEntryVendor = { navController.navigate(DestinasiEntryVendor.route) },
                onDetailVendor = { id_vendor ->
                    navController.navigate("${DestinasiDetailVendor.route}/$id_vendor")
                },
                navigateToEditVendor = { id_vendor ->
                    navController.navigate("${DestinasiUpdateVendor.route}/$id_vendor")
                },
                navigateBack = { navController.popBackStack() }
            )
        }


        composable(DestinasiEntryVendor.route) {
            EntryVendorScreen(navigateBack = {
                navController.navigate(DestinasiHomeVendor.route) {
                    popUpTo(DestinasiHomeVendor.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiDetailVendor.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailVendor.ID_VENDOR) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_vendor = backStackEntry.arguments?.getInt(DestinasiDetailVendor.ID_VENDOR)
            id_vendor?.let {
                DetailVendorScreen(
                    idVendor = id_vendor,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = DestinasiUpdateVendor.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateVendor.ID_VENDOR) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_vendor = backStackEntry.arguments?.getInt(DestinasiUpdateVendor.ID_VENDOR)
            id_vendor?.let {
                UpdateVendorScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeVendor.route) {
                            popUpTo(DestinasiHomeVendor.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        // Acara
        composable(
            route = DestinasiHomeAcara.route
        ) {
            HomeScreenAcara(
                navigateToItemEntryAcara = {
                    navController.navigate(DestinasiEntryAcara.route)
                },
                navigateBack = { navController.popBackStack() },
                navigateToItemDetailAcara = { id_acara ->
                    navController.navigate("${DestinasiDetailAcara.route}/$id_acara")
                },
                navigateToEditAcara = { id_acara ->
                    navController.navigate("${DestinasiUpdateAcara.route}/$id_acara")
                },
                navigateToHome = { navController.navigate(DestinasiAwal.route) }
            )
        }
        composable(
            route = DestinasiDetailAcara.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAcara.ID_ACARA) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_acara = backStackEntry.arguments?.getInt(DestinasiDetailAcara.ID_ACARA)
            id_acara?.let {
                DetailAcaraScreen(
                    idAcara = id_acara,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }


        composable(DestinasiEntryAcara.route) {
            EntryAcaraScreen(navigateBack = {
                navController.navigate(DestinasiHomeAcara.route) {
                    popUpTo(DestinasiHomeAcara.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = DestinasiUpdateAcara.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAcara.ID_ACARA) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_acara = backStackEntry.arguments?.getInt(DestinasiUpdateAcara.ID_ACARA)
            id_acara?.let {
                UpdateAcaraScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeAcara.route) {
                            popUpTo(DestinasiHomeAcara.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

