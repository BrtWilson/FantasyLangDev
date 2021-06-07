package edu.byu.cs.client.view.main.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shared.model.domain.Dictionary;
import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.client.presenter.DictionaryPresenter;
import edu.byu.cs.client.view.asyncTasks.DictionaryTask;
import edu.byu.cs.tweeter.R;

public class dictionaryFragment extends Fragment {

    private static final String LANGUAGE_KEY = "";

    private Language language;
    private DictionaryPresenter presenter;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private DictionaryRecyclerViewAdapter recyclerViewAdapter;

    public static dictionaryFragment newInstance(Language language) {
        dictionaryFragment fragment = new dictionaryFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(LANGUAGE_KEY, (Serializable) language);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_list, container, false);

        assert getArguments() != null;
        language = (Language) getArguments().getSerializable(LANGUAGE_KEY);
        presenter = new DictionaryPresenter((DictionaryPresenter.View) this.getView());

//        TextView textView = view.findViewById(R.id.editTexView);
//        textView.setText(language.getLanguageName());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new DictionaryRecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new DictionaryRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class DictionaryHolder extends RecyclerView.ViewHolder {

        private final TextView fantasyWord;
        private final TextView translation;

        DictionaryHolder(@NonNull View itemView) {
            super(itemView);

            fantasyWord = itemView.findViewById(R.id.recycler_item_fantasy_word);
            translation = itemView.findViewById(R.id.recycler_item_translation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //***
                }
            });
        }

        void bindDictionary(Dictionary dictionary) {
            language.setLanguageID(dictionary.getLanguageID());
            fantasyWord.setText(dictionary.getFantasyWord());
            translation.setText(dictionary.getTranslation());
        }

//        @Override
//        public void handleException(Exception e) {
//            //
//        }
    }

    private class DictionaryRecyclerViewAdapter extends RecyclerView.Adapter<DictionaryHolder> implements DictionaryTask.Observer, DictionaryPresenter.View {

        private final List<Dictionary> dictionaries = new ArrayList<>();

        private Dictionary lastDictionary;
        private Dictionary loadingDictionary = new Dictionary("loading", "loading", "loading", "loading");

        private boolean hasMorePages;
        private boolean isLoading = false;

        DictionaryRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Dictionary> newDictionaries) {
            int startInsertPosition = dictionaries.size();
            dictionaries.addAll(newDictionaries);
            this.notifyItemRangeInserted(startInsertPosition, newDictionaries.size());
        }

        void addItem(Dictionary dictionary) {
            dictionaries.add(dictionary);
            this.notifyItemInserted(dictionaries.size() - 1);
        }

        void removeItem(Dictionary dictionary) {
            int position = dictionaries.indexOf(dictionary);
            dictionaries.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(dictionaryFragment.this.getContext());
            View view;

            if (viewType == LOADING_DATA_VIEW) {
                view = inflater.inflate(R.layout.loading_row, parent, false);
            }
            else {
                view = inflater.inflate(R.layout.recycler_item, parent, false);
            }

            return new DictionaryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
            if(!isLoading) holder.bindDictionary(dictionaries.get(position));
        }

        @Override
        public int getItemCount() {
            return dictionaries.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == dictionaries.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();
            DictionaryTask task = new DictionaryTask(presenter, this);
            DictionaryPageRequest request = new DictionaryPageRequest(language.getLanguageID(), PAGE_SIZE, lastDictionary);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        }

        @Override
        public void dictionariesRetrieved(DictionaryPageResponse response) {
            List<Dictionary> dictionaries = response.getWords();

            lastDictionary = (dictionaries.size() > 0) ? dictionaries.get(dictionaries.size()-1) : null;
            hasMorePages = response.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            recyclerViewAdapter.addItems(dictionaries);
        }

        @Override
        public void handleException(Exception e) {
            removeLoadingFooter();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        private void addLoadingFooter() {
            addItem(loadingDictionary);
        }

        private void removeLoadingFooter() { removeItem(loadingDictionary); }
    }

    private class DictionaryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        DictionaryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!recyclerViewAdapter.isLoading && recyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    recyclerViewAdapter.loadMoreItems();
                }
            }
//            if (dy > 0) {
//                System.out.println("***HI***");
//            }
//            else {
//                System.out.println("***BYE***");
//            }
        }
    }
}
