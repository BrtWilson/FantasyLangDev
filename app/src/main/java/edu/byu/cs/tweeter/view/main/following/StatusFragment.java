package edu.byu.cs.tweeter.view.main.following;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.response.StatusArrayResponse;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.presenter.StatusArrayPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStatusArrayTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.main.UserPageActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Status' tab.
 */
public class StatusFragment extends Fragment implements StatusArrayPresenter.View {

    private static final String LOG_TAG = "StatusFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String IS_FEED_KEY = "IsFeed";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private StatusArrayPresenter presenter;

    private StatusRecyclerViewAdapter statusRecyclerViewAdapter;

    private Boolean isFeed;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static StatusFragment newInstance(User user, AuthToken authToken, Boolean isFeed) {
        StatusFragment fragment = new StatusFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);
        args.putSerializable(IS_FEED_KEY, isFeed);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);
        isFeed = (Boolean) getArguments().getSerializable(IS_FEED_KEY);

        presenter = new StatusArrayPresenter(this);

        RecyclerView statusRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        statusRecyclerView.setLayoutManager(layoutManager);

        statusRecyclerViewAdapter = new StatusRecyclerViewAdapter();
        statusRecyclerView.setAdapter(statusRecyclerViewAdapter);

        statusRecyclerView.addOnScrollListener(new StatusRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public void update() {
        //ToDo: - hopefully this works?
        super.onResume();
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Status data.
     */
    private class StatusHolder extends RecyclerView.ViewHolder implements GetUserTask.Observer {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusTimeStamp;
        private final TextView statusMessage;
        private User targetUser;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        StatusHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                statusTimeStamp = itemView.findViewById(R.id.timeStamp);
                statusMessage = itemView.findViewById(R.id.message);

                userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       // Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        if(targetUser.equals(user)) {
                            intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
                            intent.putExtra(MainActivity.AUTH_TOKEN_KEY, authToken);
                        }
                        else {
                            intent = new Intent(getActivity(), UserPageActivity.class);
                            intent.putExtra(UserPageActivity.TARGET_USER_KEY, targetUser);
                            intent.putExtra(UserPageActivity.CURRENT_USER_KEY, user);
                            intent.putExtra(UserPageActivity.AUTH_TOKEN_KEY, authToken);
                        }
                        startActivity(intent);
                    }
                });

                setTextViewHTML(statusMessage, statusMessage.getText().toString());

            } else {
                userImage = null;
                userAlias = null;
                userName = null;
                statusTimeStamp = null;
                statusMessage = null;
            }
        }

        //For Clickable Links
        private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
        {
            int start = strBuilder.getSpanStart(span);
            int end = strBuilder.getSpanEnd(span);
            int flags = strBuilder.getSpanFlags(span);
            ClickableSpan clickable = new ClickableSpan() {
                public void onClick(View view) {
                    // Do something with span.getURL() to handle the link click...
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(span.getURL()));
                    startActivity(browserIntent);
                }
            };
            strBuilder.setSpan(clickable, start, end, flags);
            strBuilder.removeSpan(span);
        }

        private void setTextViewHTML(TextView text, String html)
        {
            CharSequence sequence = Html.fromHtml(html);
            SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
            URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
            for(URLSpan span : urls) {
                makeLinkClickable(strBuilder, span);
            }
            text.setText(strBuilder);
            text.setMovementMethod(LinkMovementMethod.getInstance());
        }

        //For Clickable @s
        private void makeAliasClickable(String message, String alias, SpannableStringBuilder strBuilder)
        {
            int start = message.indexOf(alias);
            int end = message.lastIndexOf(alias);
            int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
            ClickableSpan clickable = new ClickableSpan() {
                public void onClick(View view) {
                    //GO TO PAGE ACTIVITY
                    if(alias.equals(user.getAlias())) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra(MainActivity.CURRENT_USER_KEY, user);
                        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, authToken);
                        startActivity(intent);
                    }
                    else {
                        goToAliasUser(alias);
                    }
                }
            };
            strBuilder.setSpan(clickable, start, end, flags);
        }

        private void goToAliasUser(String alias) {
            GetUserTask getAliasUser = new GetUserTask(presenter, this);
            UserRequest userRequest = new UserRequest(alias);
            getAliasUser.execute(userRequest);
        }


        /**
         * Binds the status's data to the view.
         *
         * @param status the status.
         */
        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getCorrespondingUser().getImageBytes()));
            userAlias.setText(status.getCorrespondingUser().getAlias());
            userName.setText(status.getCorrespondingUser().getName());
            statusTimeStamp.setText(status.getDate());
            statusMessage.setText(status.getMessage());
            targetUser = status.getCorrespondingUser();
        }

        @Override
        public void userRetrieved(UserResponse userResponse) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(MainActivity.CURRENT_USER_KEY, userResponse.getUser().getAlias());
            intent.putExtra(MainActivity.AUTH_TOKEN_KEY, authToken);
            startActivity(intent);
        }

        @Override
        public void handleException(Exception exception) {
            Toast.makeText(getContext(), "Invald User Alias!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Status data. = itemView.findViewById(R.id.statusMessage)
     */
    private class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusHolder> implements GetStatusArrayTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of Status data.
         */
        StatusRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new statuses to the list from which the RecyclerView retrieves the statuses it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newstatuses the statuses to add.
         */
        void addItems(List<Status> newstatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newstatuses);
            this.notifyItemRangeInserted(startInsertPosition, newstatuses.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the statuses it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param status the user to add.
         */
        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the statuses it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param status the user to remove.
         */
        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a Status to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StatusFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StatusHolder(view, viewType);
        }

        /**
         * Binds the Status at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param StatusHolder the ViewHolder to which the Status should be bound.
         * @param position the position (in the list of Statuses) that contains the Status to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull StatusHolder StatusHolder, int position) {
            if(!isLoading) {
                StatusHolder.bindStatus(statuses.get(position));
            }
        }

        /**
         * Returns the current number of Statuses available for display.
         * @return the number of Statuses available for display.
         */
        @Override
        public int getItemCount() {
            return statuses.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more Status
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetStatusArrayTask getStatusTask = new GetStatusArrayTask(presenter, this);
            StatusArrayRequest request = new StatusArrayRequest(user.getAlias(), PAGE_SIZE, (lastStatus == null ? null : lastStatus.getDate()), isFeed);
            getStatusTask.execute(request);
        }

        /**
         * A callback indicating more Status data has been received. Loads the new Statuses
         * and removes the loading footer.
         *
         * @param statusArrayResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void statusArrayRetrieved(StatusArrayResponse statusArrayResponse) {
            List<Status> statuses = statusArrayResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = statusArrayResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            statusRecyclerViewAdapter.addItems(statuses);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of statuses so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            User tempUser = new User("Dummy", "User", "");
            addItem(new Status("Dummy status. You underestimate my power.", "A long time ago at 9:30pm", tempUser));
        }

        /**
         * Removes the dummy user from the list of statuses so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class StatusRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        StatusRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!statusRecyclerViewAdapter.isLoading && statusRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    statusRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
