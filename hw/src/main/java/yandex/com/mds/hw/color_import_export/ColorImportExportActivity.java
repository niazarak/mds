package yandex.com.mds.hw.color_import_export;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import yandex.com.mds.hw.R;

public class ColorImportExportActivity extends AppCompatActivity {
    private static final String TAG = ColorImportExportActivity.class.getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ColorImportExportFragment())
                .commit();
    }

    public static class ColorImportExportFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        public static final String SHARED_PREFERENCES_NAME = "colors_import_export";
        public static final String IMPORT_EXPORT_FILE_PREFERENCE = "import_export_file";
        public static final String DEFAULT_FILENAME = "colors.json";
        private ProgressDialog progressDialog;
        private ColorImporterExporter exporter;
        private String importExportFilename;

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            SharedPreferences s = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
            importExportFilename = s.getString(IMPORT_EXPORT_FILE_PREFERENCE, DEFAULT_FILENAME);
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.dismiss();

            getPreferenceManager().setSharedPreferencesName(SHARED_PREFERENCES_NAME);
            getPreferenceManager().setSharedPreferencesMode(MODE_PRIVATE);
            addPreferencesFromResource(R.xml.pref_color);
            exporter = ColorImporterExporter.getInstance(getActivity());
            exporter.setExportListener(new ColorImporterExporter.OnColorsExportListener() {
                @Override
                public void OnColorsExport(ColorImporterExporter.ImportExportStatus status) {
                    if (status.progress == 0.0) {
                        progressDialog.setMessage(status.message);
                        progressDialog.setMax(2);
                        progressDialog.show();
                    } else if (status.progress == 1.0) {
                        progressDialog.dismiss();
                        showResultToast(true, true);
                    } else if (status.progress == -1.0) {
                        progressDialog.dismiss();
                        showResultToast(true, false);
                    } else {
                        progressDialog.setMessage(status.message);
                        progressDialog.setProgress((int) (status.progress * 2));
                    }
                }
            });
            exporter.setImportListener(new ColorImporterExporter.OnColorsImportListener() {
                @Override
                public void OnColorsImport(ColorImporterExporter.ImportExportStatus status) {
                    if (status.progress == 0.0) {
                        progressDialog.setMessage(status.message);
                        progressDialog.setMax(100);
                        progressDialog.show();
                    } else if (status.progress == 1.0) {
                        progressDialog.dismiss();
                        showResultToast(false, true);
                    } else if (status.progress == -1.0) {
                        progressDialog.dismiss();
                        showResultToast(false, false);
                    } else {
                        progressDialog.setMessage(status.message);
                        progressDialog.setProgress((int) (status.progress * 100));
                    }
                }
            });
            Preference importPref = findPreference("import_colors");
            importPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(), R.string.started_color_import, Toast.LENGTH_SHORT).show();
                    exporter.importColors(importExportFilename);
                    getActivity().setResult(RESULT_OK);
                    return true;
                }
            });
            final Preference exportPref = findPreference("export_colors");
            exportPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Toast.makeText(getActivity(), R.string.started_color_export, Toast.LENGTH_SHORT).show();
                    exporter.exportColors(importExportFilename);
                    return true;
                }
            });
        }

        private void showResultToast(boolean isExport, boolean isSuccess) {
            Activity activity = getActivity();
            if (activity != null)
                if (isExport) {
                    Toast.makeText(activity, isSuccess ? R.string.success_colors_export :
                            R.string.error_colors_export, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, isSuccess ? R.string.success_colors_import :
                            R.string.error_colors_import, Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.contentEquals(IMPORT_EXPORT_FILE_PREFERENCE)) {
                importExportFilename = sharedPreferences.getString(key, DEFAULT_FILENAME);
            }
        }
    }
}
